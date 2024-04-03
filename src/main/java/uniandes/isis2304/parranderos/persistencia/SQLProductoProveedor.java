package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ProductoProveedor;

class SQLProductoProveedor {
    private final static String SQL = PersistenciaSuperAndes.SQL;

    /* ****************************************************************
     *          Atributos
     *****************************************************************/
    /**
     * El manejador de persistencia general de la aplicación
     */
    private PersistenciaSuperAndes ps;

    /* ****************************************************************
     *          Métodos
     *****************************************************************/

    /**
     * Constructor
     * @param ps - El Manejador de persistencia de la aplicación
     */
    public SQLProductoProveedor (PersistenciaSuperAndes ps)
    {
        this.ps = ps;
    }
    
    public long adicionarProductoAProveedor (PersistenceManager pm, long idproducto, long idproveedor, String nombre, int precio, float calificacion) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaProductoProveedor () + "(idproducto, idproveedor, nombre, precio, calificacion) values (?, ?, ?, ?, ?)");
        q.setParameters(idproducto, idproveedor, nombre, precio, calificacion);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Superandes, por su idsucursal
     * @param pm - El manejador de persistencia
     * @param idsucursal - El idsucursal del bodega
     * @return EL número de tuplas eliminadas
     */
    public long eliminarProductoAProveedor (PersistenceManager pm, long idproducto)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProductoProveedor () + " WHERE idproducto = ?");
        q.setParameters(idproducto);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
     * base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del bodega
     * @return El objeto BAR que tiene el identificador dado
     */
    public ProductoProveedor darProductosDeProveedorPorId (PersistenceManager pm, long idproveedor) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProductoProveedor () + " WHERE idproducto = ?");
        q.setResultClass(ProductoProveedor.class);
        q.setParameters(idproveedor);
        return (ProductoProveedor) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
     * base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos BAR
     */
    public List<ProductoProveedor> darProductoProveedores (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProductoProveedor ());
        q.setResultClass(ProductoProveedor.class);
        return (List<ProductoProveedor>) q.executeList();
    }
}