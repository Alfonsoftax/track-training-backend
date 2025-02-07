package com.src.train.track.general.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class DefaultStringType implements UserType<String> {

    public static final String NAME = "defaultString";

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<String> returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(final String x, final String y) {
        if (x == y) {
            return true;
        }
        if (x == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(final String x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public String nullSafeGet(final ResultSet rs, final int position, final SharedSessionContractImplementor session,
            final Object owner) throws SQLException {
        final String value = rs.getString(position);
        if (rs.wasNull()) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final String value, final int index,
            final SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, value);
        }
    }

    @Override
    public String deepCopy(final String value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final String value) {
        return null;
    }

    @Override
    public String assemble(final Serializable cached, final Object owner) {
        return null;
    }

    @Override
    public String replace(final String detached, final String managed, final Object owner) {
        return detached;
    }

}
