import java.sql.*;

import static org.postgresql.jdbc.SslMode.VALUES;

public class BaseDeDatos {

    private final static String DB = "xyz";

    private final static String USUARIO = "postgres";

    private final static String PASSWORD = "1234";

    private final static String URL = "jdbc:postgresql://localhost:5432/" + DB;

    public boolean insertarUsuario(String usuario,String contrasena, String nombre,
                                String apellido,String direccion,String eMail,
                                String tipoUsuario, String sede, int celular){

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            System.out.println("Establishing connection with the database...");
            Statement statement = connection.createStatement();
            System.out.println("Connected to PostgreSQL database!");

            String sql ="INSERT INTO public.empleados(user_alias, password, names,"+
                    "surnames, address, phone_number, email, user_type,"+
                    "headquarter, active) " +
                    "VALUES ('" +
                    usuario + "', crypt('" + contrasena + "', gen_salt('md5')),'" +
                    nombre + "','" + apellido + "','" + direccion + "'," +
                    celular + ",'" + eMail + "','"+ tipoUsuario + "','" +
                    sede + "'," +"true);";
            System.out.print(sql);
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.execute();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }

    public boolean verificarId(int id) {

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT * from empleados";
            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet rs = psSql.executeQuery();
            int verify = 0;
            
            while (rs.next()){
                if(Integer.parseInt(rs.getString("id"))==id){
                    verify++;
                }
            }

            if(verify==1){
                return true;
            }else return false;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerS(int identifier, String campo) {

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT "+campo+" FROM empleados WHERE id = "+identifier;
            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet rs = psSql.executeQuery();

            rs.next();
            String resultado = rs.getString(1);
            return resultado;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return "";
        }
    }

    public boolean obtenerB(int identifier, String campo) {

        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="SELECT "+campo+" FROM empleados WHERE id = "+identifier;
            PreparedStatement psSql = connection.prepareStatement(sql);
            ResultSet rs = psSql.executeQuery();

            rs.next();
            boolean resultado = rs.getBoolean(1);
            return resultado;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarUsuario(int identifier, String usuario, String nombres, String apellidos,
                                     String direccion, int celular, String email, String tipo, String sede, boolean activo) {
        try (Connection connection = DriverManager.getConnection(URL,USUARIO,PASSWORD)) {

            String sql ="UPDATE empleados SET user_alias = '"+usuario+"', names = '"+nombres+
                    "', surnames = '"+apellidos+"', address = '"+direccion+"', phone_number = "+celular+
                    ", email = '"+email+"', user_type = '"+tipo+"', headquarter = '"+sede+"', active = "+activo+
                    " WHERE id = "+identifier+";";
            PreparedStatement psSql = connection.prepareStatement(sql);
            psSql.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace();
            return false;
        }
    }
}