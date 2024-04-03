package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ContieneEstante;

class SQLContieneEstante {
	
	 private final static String SQL = PersistenciaSuperAndes.SQL;
	
	 private PersistenciaSuperAndes ps;

	 public SQLContieneEstante(PersistenciaSuperAndes ps) {
			
		 this.ps = ps;
	 }
	 public long adicionarContieneEstante (PersistenceManager pm, long idproducto, long idestante, long cantidad) 
		{
			
	        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaContieneEstante () + "(idproducto, idestante, cantidad) values (?, ?, ?)");
	        q.setParameters(idproducto, idestante, cantidad);
	        return (long) q.executeUnique();
		}
	 
	 public ContieneEstante darContieneEstante (PersistenceManager pm, long idproducto) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaContieneEstante () + " WHERE idproducto = ?");
			q.setResultClass(ContieneEstante.class);
			q.setParameters(idproducto);
			return (ContieneEstante) q.executeUnique();
		}
	 
	 public long actualizarContieneEstante (PersistenceManager pm,long cantidad, long idproducto) 
	 {
			 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaContieneEstante () + " SET cantidad = cantidad + ?  WHERE idproducto = ?");
		     q.setParameters( cantidad, idproducto);
		     return (long) q.executeUnique();            
	 }
	 

}
