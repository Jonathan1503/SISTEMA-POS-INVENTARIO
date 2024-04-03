package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.RolUsuario;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto Rol Usuario de Superandes
 */
class SQLRolUsuario 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
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
	public SQLRolUsuario (PersistenciaSuperAndes ps)
	{
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un RolUsuario a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del rol
	 * @param nombre - El nombre del rol
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarRolUsuario (PersistenceManager pm, long id, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaRolUsuario  () + "(id, nombre) values (?, ?)");
        q.setParameters(id, nombre);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarRolUsuarioPorNombre (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaRolUsuario  () + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarRolUsuarioPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaRolUsuario  () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de bebida
	 * @return El objeto RolUsuario que tiene el identificador dado
	 */
	public RolUsuario darRolUsuarioPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaRolUsuario  () + " WHERE id = ?");
		q.setResultClass(RolUsuario.class);
		q.setParameters(id);
		return (RolUsuario) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN rol de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto RolUsuario que tiene el nombre dado
	 */
	public List<RolUsuario> darRolUsuarioPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaRolUsuario  () + " WHERE nombre = ?");
		q.setResultClass(RolUsuario.class);
		q.setParameters(nombre);
		return (List<RolUsuario>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS roles de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RolUsuario
	 */
	public List<RolUsuario> darRolesUsuario (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaRolUsuario  ());
		q.setResultClass(RolUsuario.class);
		return (List<RolUsuario>) q.executeList();
	}

}
