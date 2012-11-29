package com.cocoahero.android.essentials.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

public class SQLiteCursorLoader extends AsyncTaskLoader<Cursor> {

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private final SQLiteDatabase mDatabase;
    private String mTableName;
    private String mQuery;
    private String mGroupBy;
    private String mHaving;
    private String mOrderBy;
    private String mLimit;
    private String[] mProjection;
    private String[] mQueryParams;
    private boolean mDistinct;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public SQLiteCursorLoader(Context context, SQLiteDatabase db) {
        this(context, db, null, null, null, null);
    }

    public SQLiteCursorLoader(Context context, SQLiteDatabase db, String tableName) {
        this(context, db, tableName, null, null, null);
    }

    public SQLiteCursorLoader(Context context, SQLiteDatabase db, String tableName, String[] projection, String query, String[] queryParams) {
        super(context);
        this.mDatabase = db;
        this.mTableName = tableName;
        this.mProjection = projection;
        this.mQuery = query;
        this.mQueryParams = queryParams;
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public void setTableName(String aTableName) {
        this.mTableName = aTableName;
    }

    public String getTableName() {
        return this.mTableName;
    }

    public void setProjection(String[] aProjection) {
        this.mProjection = aProjection;
    }

    public String[] getProjection() {
        return this.mProjection;
    }

    public void setQuery(String aQuery) {
        this.mQuery = aQuery;
    }

    public String getQuery() {
        return this.mQuery;
    }

    public void setQueryParams(String[] queryParams) {
        this.mQueryParams = queryParams;
    }

    public String[] getQueryParams() {
        return this.mQueryParams;
    }

    public void setGroupBy(String groupBy) {
        this.mGroupBy = groupBy;
    }

    public String getGroupBy() {
        return this.mGroupBy;
    }

    public void setHavingFilter(String aHavingFilter) {
        this.mHaving = aHavingFilter;
    }

    public String getHavingFilter() {
        return this.mHaving;
    }

    public void setOrderBy(String orderBy) {
        this.mOrderBy = orderBy;
    }

    public String getOrderBy() {
        return this.mOrderBy;
    }

    public void setLimit(int limit) {
        this.mLimit = String.valueOf(limit);
    }

    public void setLimit(String limit) {
        this.mLimit = limit;
    }

    public int getLimitInt() {
        return Integer.valueOf(this.mLimit);
    }

    public String getLimit() {
        return this.mLimit;
    }
    
    public void setDistinct(boolean distinct) {
        this.mDistinct = distinct;
    }
    
    public boolean getDistinct() {
        return this.mDistinct;
    }

    @Override
    public Cursor loadInBackground() {
        if (this.isDatabaseAvailable()) {
            return this.mDatabase.query(this.mDistinct, this.mTableName, this.mProjection, this.mQuery, this.mQueryParams, this.mGroupBy, this.mHaving, this.mOrderBy, this.mLimit);
        }
        return null;
    }
    
    // ------------------------------------------------------------------------
    // Private Methods
    // ------------------------------------------------------------------------

    private boolean isDatabaseAvailable() {
        return (this.mDatabase != null) && (this.mDatabase.isOpen());
    }

}
