package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Carrito;

class SQLCarrito {
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
    public SQLCarrito (PersistenciaSuperAndes ps)
    {
        this.ps = ps;
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para adicionar un Carrito a la base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @param id_cliente - El identificador del cliente
     * @param id_producto - El id del producto
     * @return EL número de tuplas insertadas
     */
    public long adicionarCarrito (PersistenceManager pm,long id) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCarrito  () + " values (?,0)");
        q.setParameters(id);
        return (long) q.executeUnique();            
    }
    
    public long asignarCarrito (PersistenceManager pm,long idcarrito,long idcliente) 
    {
        Query q = pm.newQuery(SQL, "UPDATE "+ps.darTablaCarrito()+" SET IDCLIENTE = ? WHERE ID= ? ");
        q.setParameters(idcliente,idcarrito);
        return (long) q.executeUnique();            
    }
    
    public List<Carrito> darCarritosDisponibles(PersistenceManager pm){
    	 Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarrito  () + " WHERE idcliente = 0");
         q.setResultClass(Carrito.class);
         return (List<Carrito>) q.executeList();
    }
    
    public Carrito darCarritoCliente(PersistenceManager pm, long idcliente){
   	    Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarrito  () + " WHERE idcliente = ? ");
        q.setResultClass(Carrito.class);
        q.setParameters(idcliente);
        return (Carrito) q.executeUnique();
   }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar carritos de la base de datos de Superandes, por su id_producto
     * @param pm - El manejador de persistencia
     * @param id_producto - El id_producto del carrito
     * @return EL número de tuplas eliminadas
     */
    public long eliminarCarritoPorProducto (PersistenceManager pm, String id_producto)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCarrito  () + " WHERE idproducto = ?");
        q.setParameters(id_producto);
        return (long) q.executeUnique();            
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar carritos de la base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id_cliente - El identificador 
     * @return EL número de tuplas eliminadas
     */
    public long eliminarCarritoPorIdCliente (PersistenceManager pm, long id_cliente)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCarrito  () + " WHERE idcliente = ?");
        q.setParameters(id_cliente);
        return (long) q.executeUnique();            
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
     * base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id_cliente - El identificador del tipo de bebida
     * @return El objeto Carrito que tiene el identificador dado
     */
    public Carrito darCarritoPorIdCliente (PersistenceManager pm, long id_cliente) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarrito  () + " WHERE idcliente = ?");
        q.setResultClass(Carrito.class);
        q.setParameters(id_cliente);
        return (Carrito) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
     * base de datos de Superandes, por su id_producto
     * @param pm - El manejador de persistencia
     * @param id_producto - El id_producto del tipo de bebida
     * @return El objeto Carrito que tiene el id_producto dado
     */
    public List<Carrito> darCarritoPorProducto (PersistenceManager pm, String id_producto) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarrito  () + " WHERE idproducto = ?");
        q.setResultClass(Carrito.class);
        q.setParameters(id_producto);
        return (List<Carrito>) q.executeList();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOS DE BEBIDA de la 
     * base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos Carrito
     */
    public List<Carrito> darCarritos (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCarrito  ());
        q.setResultClass(Carrito.class);
        return (List<Carrito>) q.executeList();
    }
}
