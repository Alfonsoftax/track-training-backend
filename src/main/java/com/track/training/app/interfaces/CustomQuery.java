package com.track.training.app.interfaces;

import static com.querydsl.core.QueryModifiers.EMPTY;
import static com.track.training.app.utils.Safe.key;
import static org.apache.commons.lang3.StringUtils.contains;
import static com.track.training.app.interfaces.CustomQueryUtils.fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Subgraph;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.track.training.app.utils.CustomQueryFilter;
import com.track.training.app.utils.Safe;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomQuery<T> {

    public static final String ID = "id";

    public enum Type {
        FIND_ONE, //
        FIND_ALL, //
        FIND_PAGINATED
    }

    private final CustomQueryFunction<T> queryFunction;
    private final CustomQueryFunction<T> queryCountFunction;

    private final CustomQueryDslJpaRepository repository;

    public final Type type;

    public final JPAQuery<T> query;
    public final JPAQuery<?> queryCount;

    public final Predicate predicate;
    public final Object filter;
    public final Sort sort;
    public final Pageable pageable;

    public final int customQuery;
    private String[] customFetch;

    private boolean usePaginateIds = false;
    private boolean customSorted = false;

    private final List<OrderSpecifier<?>> orders = new ArrayList<>();

    // -------------------------------------------------------------------------

    private CustomQuery(final CustomQueryBuilder<T> builder) {
        this.queryFunction = builder.queryFunction;
        this.queryCountFunction = builder.queryCountFunction;
        this.repository = builder.repository;
        this.type = builder.type;
        this.query = builder.query;
        this.queryCount = builder.queryCount;
        this.predicate = builder.predicate;
        this.filter = builder.filter;
        this.sort = builder.sort;
        this.pageable = builder.pageable;
        this.customQuery = builder.customQuery;
        this.customFetch = builder.customFetch;
    }

    public CustomQuery<T> usePaginateIds(final boolean usePaginateIds) {
        this.usePaginateIds = usePaginateIds;
        return this;
    }

    public <F> F getFilter() {
        return (F) this.filter;
    }

    public Sort getSort() {
        return this.pageable != null ? this.pageable.getSort() : this.sort;
    }

    public void customFetch(final String... properties) {
        this.customFetch = properties;
    }

    public boolean isCustomFetch() {
        return ArrayUtils.isNotEmpty(this.customFetch);
    }

    public boolean isCustomFetch(final String property) {
        return this.isCustomFetch() && ArrayUtils.contains(this.customFetch, property);
    }

    // -------------------------------------------------------------------------

    public JPAQuery<T> orders(final Expression<?>... targets) {
        if (this.sort != null) {
            final String prefix = this.repository.path.toString().concat(".");
            this.sort.forEach(order -> {
                for (final Expression<?> target : targets) {
                    final String property = StringUtils.removeStart(target.toString(), prefix);
                    if (order.getProperty().equals(property)) {
                        this.order(order, target);
                    }
                }
            });
        }
        return this.query;
    }

    public CustomQuery<T> order(final Order order, final Expression<?> target) {
        this.customSorted = true;
        this.orders.add(CustomQueryUtils.order(order, target));
        return this;
    }

    // -------------------------------------------------------------------------

    public List<T> getResults() {
        Object queryResult = null;
        if (this.queryFunction != null) {
            queryResult = this.queryFunction.apply(this);
        }
        if (this.queryCountFunction != null) {
            this.queryCountFunction.apply(this);
        }
        if (this.customSorted) {
            this.query.orderBy(this.orders.toArray(new OrderSpecifier[0]));
        }
        if (this.pageable != null) {
            if (!this.customSorted) {
                this.repository.querydsl.applyPagination(this.pageable, this.query);
            } else {
                this.query.offset(this.pageable.getOffset());
                this.query.limit(this.pageable.getPageSize());
            }
        } else if (this.sort != null && !this.customSorted) {
            this.repository.querydsl.applySorting(this.sort, this.query);
        }

        if (this.type == Type.FIND_PAGINATED && this.usePaginateIds) {
            // clone & remove order by
            final AbstractJPAQuery<?, ?> queryCloned = this.query.clone();
            // fech distinct pageIds
            final PathBuilder<Object> id = this.repository.pathBuilder.get(ID);
            final List<?> pageIds = queryCloned.select(id).distinct().fetch();
            // remove old where + pagination & add where IN pageIds
            this.query.getMetadata().clearWhere();
            this.query.where(id.in(pageIds)).restrict(EMPTY);
        }

        if (this.isCustomFetch()) {
            this.addEntityGrapth();
        }
        return fetch(this.query, queryResult);
    }

    private void addEntityGrapth() {
        final EntityGraph<T> graph = this.repository.em.createEntityGraph(this.repository.path.getType());

        final Map<String, List<String>> parentChildMap = new HashMap<>();
        parentChildMap.put(null, new ArrayList<>()); // root node
        for (final String property : this.customFetch) {
            if (!property.startsWith("!")) {
                final String[] parentChild = getParentChild(property);
                final String parent = parentChild[0];
                final String child = parentChild[1];
                parentChildMap.computeIfAbsent(parent, k -> new ArrayList()).add(child);
                // add missing simple parent properties
                if (parent != null && !contains(parent, '.')) {
                    parentChildMap.compute(null, (k, v) -> {
                        if (!v.contains(parent)) {
                            v.add(parent);
                        }
                        return v;
                    });
                }
            }
        }
        addGrapth(graph, parentChildMap, null);

        this.query.setHint(EntityGraphType.LOAD.getKey(), graph);
    }

    private static void addGrapth(final Object graph, final Map<String, List<String>> parentChildMap,
            final String parent) {
        final List<String> childs = parentChildMap.get(parent);
        if (childs != null) {
            for (final String child : childs) {
                Subgraph<?> root;
                if (parent == null) {
                    root = ((EntityGraph<?>) graph).addSubgraph(child);
                } else {
                    root = ((Subgraph<?>) graph).addSubgraph(child);
                }
                final String nextParent = parent == null ? child : key(parent, child);
                addGrapth(root, parentChildMap, nextParent);
            }
        }
    }

    private static String[] getParentChild(final String property) {
        final StringBuilder result = new StringBuilder();
        final String[] aux = property.split("\\.");

        final int n = aux.length - 1;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                result.append('.');
            }
            result.append(aux[i]);
        }
        final String parent = result.length() > 0 ? result.toString() : null;
        final String child = aux[n];
        return new String[] { parent, child };
    }

    // -------------------------------------------------------------------------

    public static <T> CustomQueryBuilder<T> builder(final CustomQueryFunction<T> queryFunction) {
        return new CustomQueryBuilder<>(queryFunction);
    }

    public static <T> CustomQueryBuilder<T> builder(final CustomQueryFunction<T> queryFunction,
            final CustomQueryFunction<T> queryCountFunction) {
        return new CustomQueryBuilder<>(queryFunction, queryCountFunction);
    }

    public static <T> CustomQueryBuilder<T> builder(final JPAQuery<T> query, final Predicate predicate) {
        return new CustomQueryBuilder<>(query, predicate);
    }

    public static <T> CustomQueryBuilder<T> builder(final Predicate predicate) {
        return new CustomQueryBuilder<>(predicate);
    }

    public static final class CustomQueryBuilder<T> {
        private CustomQueryFunction<T> queryFunction;
        private CustomQueryFunction<T> queryCountFunction;
        private CustomQueryDslJpaRepository repository;
        private Type type;
        private JPAQuery<T> query;
        private JPAQuery<?> queryCount;
        private Predicate predicate;
        private Object filter;
        private Sort sort;
        private Pageable pageable;
        private int customQuery;
        private String[] customFetch;

        private CustomQueryBuilder(final CustomQueryFunction<T> queryFunction) {
            this.queryFunction = queryFunction;
            this.queryCountFunction = null;
        }

        private CustomQueryBuilder(final CustomQueryFunction<T> queryFunction,
                final CustomQueryFunction<T> queryCountFunction) {
            this.queryFunction = queryFunction;
            this.queryCountFunction = queryCountFunction;
        }

        private CustomQueryBuilder(final JPAQuery<T> query, final Predicate predicate) {
            this.query = query;
            this.predicate = predicate;
            query.where(predicate);
        }

        private CustomQueryBuilder(final Predicate predicate) {
            this.predicate = predicate;
        }

        public CustomQueryBuilder<T> repository(final CustomQueryDslJpaRepository repository) {
            this.repository = repository;
            return this;
        }

        public CustomQueryBuilder<T> type(final Type type) {
            this.type = type;
            return this;
        }

        public CustomQueryBuilder<T> query(final Supplier<JPAQuery<T>> querySupplier) {
            this.query = querySupplier.get();
            return this;
        }

        public CustomQueryBuilder<T> queryCount(final Supplier<JPAQuery<?>> queryCountSupplier) {
            this.queryCount = queryCountSupplier.get();
            return this;
        }

        public CustomQueryBuilder<T> predicate(final Predicate predicate) {
            this.predicate = predicate;
            return this;
        }

        public CustomQueryBuilder<T> filter(final Object filter) {
            this.filter = filter;
            if (this.filter instanceof final CustomQueryFilter customFilter) {
                this.customQuery = Safe.toInt(customFilter.getCustomQuery(), 0);
                this.customFetch = customFilter.getCustomFetch();
            } else {
                this.customQuery = 0;
                this.customFetch = null;
            }
            return this;
        }

        public CustomQueryBuilder<T> customFetch(final String... properties) {
            this.customFetch = properties;
            return this;
        }

        public CustomQueryBuilder<T> sort(final Sort sort) {
            this.sort = sort;
            return this;
        }

        public CustomQueryBuilder<T> pageable(final Pageable pageable) {
            this.pageable = pageable;
            return this;
        }

        public Type getType() {
            return this.type;
        }

        public Predicate getPredicate() {
            return this.predicate;
        }

        public <F> F getFilter() {
            return (F) this.filter;
        }

        public Sort getSort() {
            return this.sort;
        }

        public Pageable getPageable() {
            return this.pageable;
        }

        public boolean isCustomCount() {
            return this.queryCountFunction != null;
        }

        public CustomQuery<T> build(final Consumer<CustomQueryBuilder<T>> configure) {
            configure.accept(this);
            return this.build();
        }

        public CustomQuery<T> build() {
            return new CustomQuery<>(this);
        }
    }

}
