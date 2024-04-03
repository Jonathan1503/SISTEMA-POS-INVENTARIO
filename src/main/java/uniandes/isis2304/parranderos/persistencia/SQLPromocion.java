package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ContieneBodega;
import uniandes.isis2304.parranderos.negocio.PromPop;
import uniandes.isis2304.parranderos.negocio.Promocion;

 class SQLPromocion {
	
	private final static String SQL = PersistenciaSuperAndes.SQL;
	
	private PersistenciaSuperAndes ps;

	public SQLPromocion(PersistenciaSuperAndes ps) {
		
		this.ps = ps;
	}
	
	public long adicionarPromocion (PersistenceManager pm, long id, long idproducto, long idsucursal, String tipo, long cantidad, Timestamp fechaInicio,
			 Timestamp fechaFin) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPromocion () + "(idpromocion, idproducto, idsucursal, tipo, cantidad, fechainicio,"
        		+ " fechafin) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, idproducto, idsucursal, tipo, cantidad, fechaInicio, fechaFin);
        return (long) q.executeUnique();
	}
	
	public long eliminarPromocion (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPromocion () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}
	
	public Promocion darPromocionPorProducto (PersistenceManager pm, long idproducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaPromocion () + " WHERE idproducto = ?");
		q.setResultClass(ContieneBodega.class);
		q.setParameters(idproducto);
		return (Promocion) q.executeUnique();
	}
	
	public long actualizarPromocion (PersistenceManager pm,  int cantidad, long id) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaPromocion () + " SET cantidad = ?  WHERE IDPROMOCION = ?");
	     q.setParameters(cantidad,id);
	     return (long) q.executeUnique();            
	}
	
	public List<PromPop> darPromocionesPopulares(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT PROMOCION.IDPROMOCION, SUM(VENTA.CANTIDAD) AS VENDIDO FROM " + ps.darTablaVenta () + " INNER JOIN " + ps.darTablaPromocion () + " ON VENTA.IDPRODUCTO=PROMOCION.IDPRODUCTO GROUP BY PROMOCION.IDPROMOCION ORDER BY VENDIDO DESC");
		q.setResultClass(PromPop.class);
		return (List<PromPop>) q.executeList();
	}


}
