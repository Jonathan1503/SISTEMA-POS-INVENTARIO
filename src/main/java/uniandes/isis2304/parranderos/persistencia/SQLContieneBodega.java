package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ContieneBodega;

class SQLContieneBodega {
	 
	 private final static String SQL = PersistenciaSuperAndes.SQL;
		
	 private PersistenciaSuperAndes ps;

	 public SQLContieneBodega(PersistenciaSuperAndes ps) {
			
		 this.ps = ps;
	 }
	 public long adicionarContieneBodega (PersistenceManager pm, long idproducto, long idbodega, long cantidad) 
		{
			
	        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaContieneBodega () + "(idproducto, idbodega, cantidad) values (?, ?, ?)");
	        q.setParameters(idproducto, idbodega, cantidad);
	        return (long) q.executeUnique();
		}
	 public long actualizarContieneBodega (PersistenceManager pm,long cantidad, long idproducto) 
	 {
			 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaContieneBodega () + " SET cantidad = cantidad + ?  WHERE idproducto = ?");
		     q.setParameters( cantidad, idproducto);
		     return (long) q.executeUnique();            
	 }
	 
	 public ContieneBodega darContieneBodega (PersistenceManager pm, long id) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaContieneBodega () + " WHERE idproducto = ?");
			q.setResultClass(ContieneBodega.class);
			q.setParameters(id);
			return (ContieneBodega) q.executeUnique();
		}

}
