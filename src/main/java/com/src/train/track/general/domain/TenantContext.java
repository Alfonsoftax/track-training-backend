package com.src.train.track.general.domain;

import static com.src.train.track.general.Constants.COMPANY;
import static com.src.train.track.general.Constants.COMPANY_MAIN;
import static com.src.train.track.general.Constants.MAIN;
import static org.apache.commons.lang3.StringUtils.isAllUpperCase;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.src.train.track.general.helper.SessionUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Tenant context.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TenantContext {

    public static final String TENANT_HEADER = "tenant";

    /** The Constant TENANT. */
    private static final String TENANT = "TENANT";

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());

    /** Thread local containing current tenant. */
    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    /** List containing all declared tenants. */
    private static List<String> declaredTenants = Collections.emptyList();

    /**
     * Normalize tenant.
     *
     * @param tenant
     *            the tenant
     * @return the normalized tenant
     */
    public static String normalizeTenant(final String tenant) {
        String normalizedTenant = trimToNull(tenant);
        if (normalizedTenant != null) {
            if (!isAllUpperCase(normalizedTenant)) {
                normalizedTenant = upperCase(normalizedTenant);
            }
            if (MAIN.equals(normalizedTenant)) {
                normalizedTenant = COMPANY_MAIN.intern();
            } else {
                normalizedTenant = removeStart(normalizedTenant, COMPANY);
                if (isNumeric(normalizedTenant)) {
                    normalizedTenant = COMPANY.concat(normalizedTenant).intern();
                } else {
                    logger.warn("No valid tenant identifier: {}", tenant);
                    normalizedTenant = null;
                }
            }
        }
        return normalizedTenant;
    }

    /**
     * Parses the tenant.
     *
     * @param tenant
     *            the tenant
     * @return the parsed tenant
     */
    public static String parseTenant(final String tenant) {
        String parsedTenant = tenant;
        if (tenant != null) {
            if (MAIN.equals(tenant)) {
                parsedTenant = COMPANY_MAIN.intern();
            } else if (!StringUtils.startsWith(tenant, COMPANY)) {
                parsedTenant = COMPANY.concat(tenant).intern();
            } else {
                parsedTenant = tenant.intern();
            }
        }
        return parsedTenant;
    }

    /**
     * Sets the current tenant identifier.
     *
     * @param tenant
     *            current tenant identifier.
     * @return the parsed tenant
     */
    public static String setCurrentTenant(final String tenant) {
        String parsedTenant = parseTenant(tenant);
        if (!Objects.equals(currentTenant.get(), parsedTenant) && logger.isDebugEnabled()) {
            logger.debug("Setting tenant to {}", parsedTenant);
        }
        if (parsedTenant == null || isValidTenant(parsedTenant)) {
            currentTenant.set(parsedTenant);
            SessionUtils.getUserDetails().put(TENANT, parsedTenant);
        } else {
            logger.warn("No matching tenant identifier: {}", tenant);
            parsedTenant = null;
        }
        return parsedTenant;
    }

    /**
     * Gets the current tenant identifier.
     *
     * @return the current tenant identifier.
     */
    public static String getCurrentTenant() {
        return (String) SessionUtils.getUserDetails().computeIfAbsent(TENANT, k -> currentTenant.get());
    }

    /**
     * Gets the current company code.
     *
     * @return the current company code
     */
    public static String getCurrentCompanyCode() {
        return getCompanyCode(getCurrentTenant());
    }

    /**
     * Gets the company code.
     *
     * @param tenant
     *            the tenant
     * @return the company code
     */
    public static String getCompanyCode(final String tenant) {
        return tenant != null ? StringUtils.removeStart(tenant, COMPANY) : null;
    }

    /**
     * Clear the current tenant.
     */
    public static void clear() {
        setCurrentTenant(null);
        currentTenant.remove();
    }

    /**
     * Gets the declared tenants.
     *
     * @return the declared tenants
     */
    public static Collection<String> getDeclaredTenants() {
        return declaredTenants;
    }

    /**
     * Sets the declared tenants.
     *
     * @param declaredTenants
     *            the new declared tenants
     */
    public static void setDeclaredTenants(final Collection<Object> declaredTenants) {
        final List<String> declaredTenantsList = new ArrayList<>(declaredTenants.size());
        declaredTenants.forEach(tenant -> declaredTenantsList.add((String) tenant));
        TenantContext.declaredTenants = Collections.unmodifiableList(declaredTenantsList);
    }

    /**
     * Gets the first declared tenant.
     *
     * @return the first declared tenant
     */
    public static String getFirstTenant() {
        return declaredTenants.isEmpty() ? null : declaredTenants.get(0);
    }

    /**
     * Checks if is valid tenant.
     *
     * @param tenant
     *            the tenant
     * @return true, if is valid tenant
     */
    public static boolean isValidTenant(final String tenant) {
        return tenant != null && (COMPANY_MAIN.equals(tenant) || declaredTenants.contains(tenant));
    }

}
