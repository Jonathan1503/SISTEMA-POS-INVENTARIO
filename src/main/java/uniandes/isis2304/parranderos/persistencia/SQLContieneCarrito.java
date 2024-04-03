package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.ContieneCarrito;

class SQLContieneCarrito {
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
	    public SQLContieneCarrito (PersistenciaSuperAndes ps)
	    {
	        this.ps = ps;
	    }
	    
	    public long limpiarCarrito (PersistenceManager pm, long idcarrito)
	    {
	        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaContieneCarrito  () + " WHERE idcarrito = ?");
	        q.setParameters(idcarrito);
	        return (long) q.executeUnique();            
	    }
	    
	    public List<ContieneCarrito> darProductosCarrito(PersistenceManager pm,long idcarrito){
	    	Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaContieneCarrito () + " WHERE idcarrito = ?");
			q.setResultClass(ContieneCarrito.class);
			q.setParameters(idcarrito);
			return (List<ContieneCarrito>) q.executeList();
	    }
	    
	    public ContieneCarrito tieneProductoCarrito(PersistenceManager pm,long idcarrito,long idproducto){
	    	
	    	
	    	Query q = pm.newQuery(SQL, "SELECT * FROM "+ps.darTablaContieneCarrito()+ " WHERE idcarrito = ? AND idproducto= ?");
			q.setResultClass(ContieneCarrito.class);
			q.setParameters(idcarrito,idproducto);
			return (ContieneCarrito) q.executeUnique();
	    }
	    
	    public long adicionarProductoCarrito(PersistenceManager pm, long idcarrito, long idproducto, long cantidad) {
	    	 Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaContieneCarrito () + "  values (?, ?, ?)");
		     q.setParameters(idcarrito, idproducto, cantidad);
		     return (long) q.executeUnique();
	    }
	    
	    public long incrementarProductoCarrito(PersistenceManager pm, long idcarrito, long idproducto, long cantidad) {
	    	 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaContieneCarrito () + " SET CANTIDAD= CANTIDAD+ ? WHERE IDCARRITO= ? AND IDPRODUCTO = ? ");
		     q.setParameters(cantidad, idcarrito, idproducto);
		     return (long) q.executeUnique();
	    }
	    
	    public long quitarProductoCarrito(PersistenceManager pm, long idcarrito, long idproducto) {
	    	 Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaContieneCarrito () + " WHERE IDCARRITO= ? AND IDPRODUCTO = ?");
		     q.setParameters(idcarrito, idproducto);
		     return (long) q.executeUnique();
	    }
	    

}
