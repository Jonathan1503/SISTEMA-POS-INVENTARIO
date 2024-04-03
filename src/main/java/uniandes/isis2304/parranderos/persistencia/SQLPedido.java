package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Pedido;


class SQLPedido {
	
	private final static String SQL = PersistenciaSuperAndes.SQL;
	
	private PersistenciaSuperAndes ps;

	public SQLPedido(PersistenciaSuperAndes ps) {
		
		this.ps = ps;
	}
	
	public long adicionarPedido (PersistenceManager pm, long idpedido, long idProveedor, long idProducto, double volumen, double preciosubtotal,
			long sucursal) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPedido () + " values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idpedido, idProveedor, idProducto, volumen, preciosubtotal, sucursal);
        return (long) q.executeUnique();
	}
	
	public long asignarPedidoConsolidado(PersistenceManager pm, long idsucursal, long idproveedor, long idpedido) {
		 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaPedido () + " SET idpedido = ? WHERE sucursal = ? and idproveedor= ?");
	     q.setParameters(idpedido, idsucursal,idproveedor);
	     return (long) q.executeUnique();  
	}
	
	public List<Pedido> darPedidosPorProducto (PersistenceManager pm, long idproducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido () + " WHERE idproducto = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(idproducto);
		return (List<Pedido>) q.executeList();
	}
	
	
	
	public long actualizarPedido (PersistenceManager pm, long idpedido, Timestamp fechaEntregada, double calificacion) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaPedido () + " SET fechaentrega = ?, calificacion = ?, estado = ? WHERE idpedido = ?");
	     q.setParameters(fechaEntregada, calificacion,"Entregado", idpedido);
	     return (long) q.executeUnique();            
	}
	
	public List<Pedido> darPedidosPorId (PersistenceManager pm, long idpedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido () + " WHERE idpedido = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(idpedido);
		return (List<Pedido>) q.executeList();
	}
	
	public long eliminarPedidosPorSucursal (PersistenceManager pm, long sucursal)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPedido () + " WHERE sucursal = ?");
        q.setParameters(sucursal);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar UN PEDIDO de la base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del pedido
     * @return EL número de tuplas eliminadas
     */
    public long eliminarPedidoPorId (PersistenceManager pm, long idpedido)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPedido () + " WHERE idpedido = ?");
        q.setParameters(idpedido);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN PEDIDO de la 
     * base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del pedido
     * @return El objeto PEDIDO que tiene el identificador dado
     */
    public Pedido darPedidoPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido () + " WHERE idpedido = ?");
        q.setResultClass(Pedido.class);
        q.setParameters(id);
        return (Pedido) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PEDIDOS de la 
     * base de datos de Superandes, por su sucursal
     * @param pm - El manejador de persistencia
     * @param sucursal - El sucursal de pedido buscado
     * @return Una lista de objetos PEDIDO que tienen el sucursal dado
     */
    public List<Pedido> darPedidosPorSucursalYProveedor (PersistenceManager pm, long sucursal, long proveedor) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido () + " WHERE sucursal = ? AND idproveedor = ?");
        q.setResultClass(Pedido.class);
        q.setParameters(sucursal,proveedor);
        return (List<Pedido>) q.executeList();
    }
    
    public List<Pedido> darPedidosPorProveedor (PersistenceManager pm, long proveedor) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido () + " WHERE idproveedor = ? ");
        q.setResultClass(Pedido.class);
        q.setParameters(proveedor);
        return (List<Pedido>) q.executeList();
    }
    
    public List<Pedido> darPedidosPorSucursal (PersistenceManager pm, long sucursal) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido () + " WHERE sucursal = ? ");
        q.setResultClass(Pedido.class);
        q.setParameters(sucursal);
        return (List<Pedido>) q.executeList();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PEDIDOS de la 
     * base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos PEDIDO
     */
    public List<Pedido> darPedidos (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedido ());
        q.setResultClass(Pedido.class);
        return (List<Pedido>) q.executeList();
    }
    
    
}
