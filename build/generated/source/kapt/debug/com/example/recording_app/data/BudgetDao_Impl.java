package com.example.recording_app.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BudgetDao_Impl implements BudgetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Budget> __insertionAdapterOfBudget;

  private final EntityDeletionOrUpdateAdapter<Budget> __updateAdapterOfBudget;

  public BudgetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBudget = new EntityInsertionAdapter<Budget>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `budgets` (`month`,`expectedExpense`,`expectedIncome`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Budget entity) {
        if (entity.getMonth() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMonth());
        }
        statement.bindDouble(2, entity.getExpectedExpense());
        statement.bindDouble(3, entity.getExpectedIncome());
      }
    };
    this.__updateAdapterOfBudget = new EntityDeletionOrUpdateAdapter<Budget>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `budgets` SET `month` = ?,`expectedExpense` = ?,`expectedIncome` = ? WHERE `month` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Budget entity) {
        if (entity.getMonth() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMonth());
        }
        statement.bindDouble(2, entity.getExpectedExpense());
        statement.bindDouble(3, entity.getExpectedIncome());
        if (entity.getMonth() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMonth());
        }
      }
    };
  }

  @Override
  public Object insertBudget(final Budget budget, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBudget.insert(budget);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateBudget(final Budget budget, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBudget.handle(budget);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<Budget> getBudgetByMonth(final String month) {
    final String _sql = "SELECT * FROM budgets WHERE month = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (month == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, month);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budgets"}, new Callable<Budget>() {
      @Override
      @Nullable
      public Budget call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfExpectedExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedExpense");
          final int _cursorIndexOfExpectedIncome = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedIncome");
          final Budget _result;
          if (_cursor.moveToFirst()) {
            final String _tmpMonth;
            if (_cursor.isNull(_cursorIndexOfMonth)) {
              _tmpMonth = null;
            } else {
              _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
            }
            final double _tmpExpectedExpense;
            _tmpExpectedExpense = _cursor.getDouble(_cursorIndexOfExpectedExpense);
            final double _tmpExpectedIncome;
            _tmpExpectedIncome = _cursor.getDouble(_cursorIndexOfExpectedIncome);
            _result = new Budget(_tmpMonth,_tmpExpectedExpense,_tmpExpectedIncome);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
