package cn.utoverse.utoverselib.database;

public class DatabaseManagerImpl implements IDatabaseManager {

    private DatabaseCoreImpl dbCore;

    public DatabaseManagerImpl(DatabaseCoreImpl dbCore) {
        this.dbCore = dbCore;
    }

}
