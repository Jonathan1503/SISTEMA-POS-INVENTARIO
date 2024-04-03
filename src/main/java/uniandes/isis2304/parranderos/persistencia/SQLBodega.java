package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.resulIndice;

class SQLBodega {
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperAndes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param ps - El Manejador de persistencia de la aplicación
	 */
	public SQLBodega (PersistenciaSuperAndes ps)
	{
		this.ps = ps;
	}
	
	public long adicionarBodega (PersistenceManager pm, long id, long idsucursal, int volumen_max, int peso_max, long categoria_almac) 
	{
		
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaBodega () + "(id, idsucursal, volumenmax, pesomax,  categoriaalmac) values (?, ?, ?, ?, ?)");
        q.setParameters(id, idsucursal, volumen_max, peso_max,  categoria_almac);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Superandes, por su idsucursal
	 * @param pm - El manejador de persistencia
	 * @param idsucursal - El idsucursal del bodega
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarBodegasPorSucursal (PersistenceManager pm, long idsucursal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaBodega () + " WHERE idsucursal = ?");
        q.setParameters(idsucursal);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del bodega
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarBodegaPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaBodega () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del bodega
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Bodega darBodegaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaBodega () + " WHERE id = ?");
		q.setResultClass(Bodega.class);
		q.setParameters(id);
		return (Bodega) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Superandes, por su idsucursal
	 * @param pm - El manejador de persistencia
	 * @param idsucursal - El idsucursal de bodega buscado
	 * @return Una lista de objetos BAR que tienen el idsucursal dado
	 */
	public List<Bodega> darBodegasPorSucursal (PersistenceManager pm, long idsucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaBodega () + " WHERE idsucursal = ?");
		q.setResultClass(Bodega.class);
		q.setParameters(idsucursal);
		return (List<Bodega>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Bodega> darBodegas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaBodega ());
		q.setResultClass(Bodega.class);
		return (List<Bodega>) q.executeList();
	}
	
	public List<resulIndice> darIndiceOcupacionBodegasSuc (PersistenceManager pm, long idsucursal)
	{
		Query q = pm.newQuery(SQL, "SELECT aa.IDBODEGA,AA.VOLACTUAL/BODEGA.VOLUMENMAX AS INDICEOCUPACION FROM(SELECT CONTIENE_BODEGA.IDBODEGA,CONTIENE_BODEGA.CANTIDAD*PRODUCTO.VOLUMEN AS VOLACTUAL FROM " + ps.darTablaContieneBodega() + ","+ ps.darTablaProducto() + " WHERE CONTIENE_BODEGA.IDPRODUCTO = PRODUCTO.ID)AA, " + ps.darTablaBodega()	+ " WHERE BODEGA.ID= AA.IDBODEGA AND BODEGA.IDSUCURSAL= ? ORDER BY BODEGA.ID");
		q.setResultClass(resulIndice.class);
		q.setParameters(idsucursal);
		return (List<resulIndice>) q.executeList();
	}
	
	public List<resulIndice> darIndiceOcupacionBodegas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT aa.IDBODEGA,AA.VOLACTUAL/BODEGA.VOLUMENMAX AS INDICEOCUPACION FROM(SELECT CONTIENE_BODEGA.IDBODEGA,CONTIENE_BODEGA.CANTIDAD*PRODUCTO.VOLUMEN AS VOLACTUAL FROM " + ps.darTablaContieneBodega() + ","+ ps.darTablaProducto() + " WHERE CONTIENE_BODEGA.IDPRODUCTO = PRODUCTO.ID)AA, " + ps.darTablaBodega()	+ " WHERE BODEGA.ID= AA.IDBODEGA  ORDER BY BODEGA.ID");
		q.setResultClass(resulIndice.class);
		
		return (List<resulIndice>) q.executeList();
	}
	


}