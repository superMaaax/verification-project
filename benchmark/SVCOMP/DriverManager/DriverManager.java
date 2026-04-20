class SQLException extends Exception {}

class Statement {

  public void execute(String s) throws SQLException {
    if (s.contains("<bad/>")) {
      assert false;
    }
  }

  public void executeUpdate(String s, Object... o) throws SQLException {
    if (s.contains("<bad/>")) {
      assert false;
    }
  }

  public void executeQuery(String s) {
    if (s.contains("<bad/>")) {
      assert false;
    }
  }
}

class Connection {

  public void prepareStatement(String s) throws SQLException {
    if (s.contains("<bad/>")) {
      assert false;
    }
  }

  public void close() throws SQLException {}

  public Statement createStatement() {
    return new Statement();
  }
}

public class DriverManager {
  public static Connection getConnection(String connectionString) {
    return new Connection();
  }
}
