package cn.utoverse.utoverselib.database;

public class DatabaseHelperImpl implements IDatabaseHelper {

    private DatabaseCoreImpl dbCore;

    public DatabaseHelperImpl(DatabaseCoreImpl dbCore) {
        this.dbCore = dbCore;
    }
}
