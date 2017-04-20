package com.delta.swiftq.database;

import java.sql.SQLException;

/**
 * Created by matthewbrighty on 01/03/2017.
 */
public class ConnectionManagerException extends Exception{
    ConnectionManagerException(String message){
        super(message);
    }

    ConnectionManagerException(Throwable cause){
        super(cause);
    }
}
