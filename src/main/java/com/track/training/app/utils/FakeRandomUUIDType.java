package com.track.training.app.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class FakeRandomUUIDType implements UserType<UUID> {

    public static final String NAME = "randomUUID";

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<UUID> returnedClass() {
        return UUID.class;
    }

    @Override
    public boolean equals(final UUID x, final UUID y) {
        if (x == y) {
            return true;
        }
        if (x == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(final UUID x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public UUID nullSafeGet(final ResultSet rs, final int position, final SharedSessionContractImplementor session,
            final Object owner) throws SQLException {
        return UUID.randomUUID();
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final UUID value, final int index,
            final SharedSessionContractImplementor session) throws SQLException {
        // Do nothing
    }

    @Override
    public UUID deepCopy(final UUID value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final UUID value) {
        return null;
    }

    @Override
    public UUID assemble(final Serializable cached, final Object owner) {
        return null;
    }

    @Override
    public UUID replace(final UUID detached, final UUID managed, final Object owner) {
        return detached;
    }

}
