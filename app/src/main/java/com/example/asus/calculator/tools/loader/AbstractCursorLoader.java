package com.example.asus.calculator.tools.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;


public abstract class AbstractCursorLoader extends AsyncTaskLoader<Cursor> {
    private Cursor cursor;

    public AbstractCursorLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(Cursor data) {
        if (isReset()) {
            if (data != null) {
                data.close();
            }
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if (cursor != null) {
            deliverResult(cursor);
        }
        if (takeContentChanged() || cursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor data) {
        if (data != null && !data.isClosed()) {
            data.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (cursor != null && cursor.isClosed()) {
            cursor.close();
        }
        cursor = null;
    }
}
