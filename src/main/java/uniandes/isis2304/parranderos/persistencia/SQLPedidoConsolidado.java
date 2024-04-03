package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Pedido;
import uniandes.isis2304.parranderos.negocio.PedidoConsolidado;

class SQLPedidoConsolidado {
	
	private final static String SQL = PersistenciaSuperAndes.SQL;
	
	private PersistenciaSuperAndes ps;

	public SQLPedidoConsolidado(PersistenciaSuperAndes ps) {
		
		this.ps = ps;
	}
	
	public long consolidarPedidos (PersistenceManager pm, long idpedido,long idproveedor, double preciototal,Timestamp fechaesperada, long sucursal)
			
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPedidoConsolidado () + " values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idpedido, idproveedor,preciototal, fechaesperada,new Timestamp(0), 0, "Pendiente",sucursal);
        return (long) q.executeUnique();
	}
	
	public long actualizarPedidoConsolidado (PersistenceManager pm, long idpedido, Timestamp fechaEntregada, double calificacion) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaPedidoConsolidado () + " SET fechaentrega = ?, calificacion = ?, estado = ? WHERE idpedido = ?");
	     q.setParameters(fechaEntregada, calificacion,"Entregado", idpedido);
	     return (long) q.executeUnique();            
	}
	
	public PedidoConsolidado darPedidoConsolidadoPorId (PersistenceManager pm, long idpedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPedidoConsolidado () + " WHERE idpedido = ?");
		q.setResultClass(PedidoConsolidado.class);
		q.setParameters(idpedido);
		return (PedidoConsolidado) q.executeUnique();
	}

}
