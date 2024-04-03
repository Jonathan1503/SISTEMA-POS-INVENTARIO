package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Usuario;

class SQLUsuario {
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
	public SQLUsuario (PersistenciaSuperAndes ps)
	{
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un USUARIO a la base de datos de Superandes
	 * @return El número de tuplas insertadas
	 */
	public long adicionarUsuario (PersistenceManager pm, long id, String nombre, String correo,String contrasena,
			String tipodoc, long numerodoc, long tipousuario, long sucursal) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaUsuario () + "(id, nombre, correo, contrasena, tipodoc, numerodoc, tipousuario, sucursal) values (?, ?, ?,?, ?, ?, ?)");
        q.setParameters(id, nombre, correo, contrasena,tipodoc, numerodoc, tipousuario, sucursal);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar USUARIOS de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del usuario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarUsuariosPorNombre (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaUsuario () + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN USUARIO de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del usuario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarUsuarioPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaUsuario () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN USUARIO de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del usuario
	 * @return El objeto USUARIO que tiene el identificador dado
	 */
	public Usuario darUsuarioPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaUsuario () + " WHERE id = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(id);
		return (Usuario) q.executeUnique();
	}
	
	public Usuario darUsuarioPorCorreo (PersistenceManager pm, String correo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaUsuario () + " WHERE correo = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(correo);
		return (Usuario) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS USUARIOS de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre de usuario buscado
	 * @return Una lista de objetos USUARIO que tienen el nombre dado
	 */
	public List<Usuario> darUsuariosPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaUsuario () + " WHERE nombre = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(nombre);
		return (List<Usuario>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS USUARIOS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos USUARIO
	 */
	public List<Usuario> darUsuarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaUsuario ());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para aumentar en uno el número de sedes de los usuarioes de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param ciudad - La ciudad a la cual se le quiere realizar el proceso
	 * @return El número de tuplas modificadas
	 */
	public long aumentarSedesUsuariosCiudad (PersistenceManager pm, String ciudad)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaUsuario () + " SET cantsedes = cantsedes + 1 WHERE ciudad = ?");
        q.setParameters(ciudad);
        return (long) q.executeUnique();
	}
}