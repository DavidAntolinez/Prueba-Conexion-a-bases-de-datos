import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MenuPrincipal extends Menu {
    public MenuPrincipal(String title) {
        super(title);
    }

    public void menu() {
        Db prueba = new Db();
        prueba.ConectarDB("root", "123", "tallerdb", "localhost", "3306");
        while (prueba.isConexion() && true) {
            int opt = Integer.parseInt(input(
                    "Menu 😎: \n1. Create. \n2. Read. \n3. Update. \n4. Delete. \n0. Salir"));

            switch (opt) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    prueba.Create(
                            Integer.parseInt(Validaciones("[0-9]+",
                                    "Ingrese el id del producto nuevo (el id no puede ser uno ya existene)")),
                            Validaciones(".+", "Ingrese el nombre del producto nuevo"),
                            Float.parseFloat(Validaciones("([0-9]+[.][0-9]+)|([0-9]+)",
                                    "Ingrese el precio del producto nuevo (si hay una componente decimal indique con un punto)")),
                            SelectFabricante(prueba));
                    break;
                case 2:
                    msgScroll(prueba.Read());
                    break;
                case 3:

                    prueba.Update(Integer.parseInt(Validaciones("[0-9]+", "Ingrese el id del producto a modificar")),
                            UpdateNombre(), UpdatePrecio(),
                            UpdateFabricante(prueba));
                    break;
                case 4:
                    prueba.Delete(Integer.parseInt(Validaciones("[0-9]+", "Ingrese el id del producto a eliminar")));
                    break;

                default:
                    msg("opcion invalida.");
                    break;
            }
        }

    }

    private String Validaciones(String patron, String msginput) {// metodo para realizar todas las valdiaciones con
                                                                 // expresiones regulares
        Pattern Patron = Pattern.compile(patron);
        String input;
        while (true) {
            input = input(msginput).trim();
            if (!Patron.matcher(input).matches()) { // validar el formato correcto
                msg("Formato invalido");
            } else
                return input;
        }
    }

    private String UpdateNombre() {
        String[] opts = { "Si", "No" };
        String selec = (String) inputSelect("Desea modificar el nombre?", "", opts);
        if (selec.equals(opts[1])) {
            return "";
        } else {
            return Validaciones(".+", "Ingrese el nombre nuevo del producto");
        }
    }

    private float UpdatePrecio() {
        String[] opts = { "Si", "No" };
        String selec = (String) inputSelect("Desea modificar el precio?", "", opts);
        if (selec.equals(opts[1])) {
            return -1;
        } else {
            return Float.parseFloat(Validaciones("([0-9]+[.][0-9]+)|([0-9]+)",
                    "Ingrese el precio nuevo del producto (si hay una componente decimal indique con un punto)"));
        }
    }

    private int UpdateFabricante(Db BD) {
        String[] opts = { "Si", "No" };
        String selec = (String) inputSelect("Desea modificar el fabricante?", "", opts);
        if (selec.equals(opts[1])) {
            return 0;
        } else {
            return SelectFabricante(BD);

        }
    }

    private int SelectFabricante(Db BD) {
        try {
            ResultSet res = BD.getSentencia().executeQuery("CALL listarFabricantes()");
            res.next();
            ArrayList<String> fabricantes = new ArrayList<String>();
            while (res.next()) {
                fabricantes.add(res.getString(2));
            }
            String selec =(String) inputSelect("Seleccione el nuevo fabricante", "",
            fabricantes.toArray(new String[fabricantes.size()]));
            int i = 0;
            while (selec != fabricantes.get(i)) {
                i++;
            }
            return i + 1;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}
