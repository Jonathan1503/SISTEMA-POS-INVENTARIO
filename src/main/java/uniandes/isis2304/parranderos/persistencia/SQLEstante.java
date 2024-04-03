package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Estante;
import uniandes.isis2304.parranderos.negocio.indiceEstante;

class SQLEstante {
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
	public SQLEstante (PersistenciaSuperAndes ps)
	{
		this.ps = ps;
	}
	
	public long adicionarEstante (PersistenceManager pm, long id, long idsucursal,  int nivel_abastecimiento, int volumen_max, int peso_max, long categoria_almac) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaEstante () + "(id, idsucursal,  nivelabastecimiento, volumenmax, pesomax, categoriaalmac) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(id, idsucursal,  nivel_abastecimiento, volumen_max, peso_max, categoria_almac);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Superandes, por su idsucursal
	 * @param pm - El manejador de persistencia
	 * @param idsucursal - El idsucursal del estante
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEstantesPorSucursal (PersistenceManager pm, long idsucursal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaEstante () + " WHERE idsucursal = ?");
        q.setParameters(idsucursal);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del estante
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEstantePorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaEstante () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del estante
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Estante darEstantePorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaEstante () + " WHERE id = ?");
		
		q.setResultClass(Estante.class);
		q.setParameters(id);
	
		
		return (Estante) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Superandes, por su idsucursal
	 * @param pm - El manejador de persistencia
	 * @param idsucursal - El idsucursal de estante buscado
	 * @return Una lista de objetos BAR que tienen el idsucursal dado
	 */
	public List<Estante> darEstantesPorSucursal (PersistenceManager pm, long idsucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaEstante () + " WHERE idsucursal = ?");
		q.setResultClass(Estante.class);
		q.setParameters(idsucursal);
		return (List<Estante>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Estante> darEstantes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaEstante ());
		q.setResultClass(Estante.class);
		return (List<Estante>) q.executeList();
	}
	
	public List<indiceEstante> darIndiceOcupacionEstantesSuc (PersistenceManager pm, long idsucursal)
	{
		Query q = pm.newQuery(SQL, "SELECT aa.IDESTANTE,AA.VOLACTUAL/ESTANTE.VOLUMENMAX AS INDICEOCUPACION FROM(SELECT CONTIENE_ESTANTE.IDESTANTE,CONTIENE_ESTANTE.CANTIDAD*PRODUCTO.VOLUMEN AS VOLACTUAL FROM " + ps.darTablaContieneEstante() + ","+ ps.darTablaProducto() + " WHERE CONTIENE_ESTANTE.IDPRODUCTO = PRODUCTO.ID)AA, " + ps.darTablaEstante()	+ " WHERE ESTANTE.ID= AA.IDESTANTE AND ESTANTE.IDSUCURSAL= ? ORDER BY ESTANTE.ID");
		q.setResultClass(indiceEstante.class);
		q.setParameters(idsucursal);
		return (List<indiceEstante>) q.executeList();
	}
	
	public List<indiceEstante> darIndiceOcupacionEstantes(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT aa.IDESTANTE,AA.VOLACTUAL/ESTANTE.VOLUMENMAX AS INDICEOCUPACION FROM(SELECT CONTIENE_ESTANTE.IDESTANTE,CONTIENE_ESTANTE.CANTIDAD*PRODUCTO.VOLUMEN AS VOLACTUAL FROM " + ps.darTablaContieneEstante() + ","+ ps.darTablaProducto() + " WHERE CONTIENE_ESTANTE.IDPRODUCTO = PRODUCTO.ID)AA, " + ps.darTablaEstante()	+ " WHERE ESTANTE.ID= AA.IDESTANTE ORDER BY ESTANTE.ID");
		q.setResultClass(indiceEstante.class);
		return (List<indiceEstante>) q.executeList();
	}
}