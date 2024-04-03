package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Categoria;
class SQLCategoria {
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
    public SQLCategoria (PersistenciaSuperAndes ps)
    {
        this.ps = ps;
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para adicionar un Categoria a la base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @param id - El identificador del categoria
     * @param nombre - El nombre del categoria
     * @return EL número de tuplas insertadas
     */
    public long adicionarCategoria (PersistenceManager pm, long id, String nombre) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCategoria  () + "(id, nombre) values (?, ?)");
        q.setParameters(id, nombre);
        return (long) q.executeUnique();            
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar Categorias de la base de datos de Superandes, por su nombre
     * @param pm - El manejador de persistencia
     * @param nombre - El nombre del categoria
     * @return EL número de tuplas eliminadas
     */
    public long eliminarCategoriaPorNombre (PersistenceManager pm, String nombre)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCategoria  () + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();            
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar Categorias de la base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del categoria
     * @return EL número de tuplas eliminadas
     */
    public long eliminarCategoriaPorId (PersistenceManager pm, long id)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCategoria  () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
     * base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del categoria
     * @return El objeto Categoria que tiene el identificador dado
     */
    public Categoria darCategoriaPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCategoria  () + " WHERE id = ?");
        q.setResultClass(Categoria.class);
        q.setParameters(id);
        return (Categoria) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN categoria de la 
     * base de datos de Superandes, por su nombre
     * @param pm - El manejador de persistencia
     * @param nombre - El nombre del categoria
     * @return El objeto Categoria que tiene el nombre dado
     */
    public List<Categoria> darCategoriaPorNombre (PersistenceManager pm, String nombre) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCategoria  () + " WHERE nombre = ?");
        q.setResultClass(Categoria.class);
        q.setParameters(nombre);
        return (List<Categoria>) q.executeList();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS categorias de la 
     * base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos Categoria
     */
    public List<Categoria> darCategorias (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCategoria  ());
        q.setResultClass(Categoria.class);
        return (List<Categoria>) q.executeList();
    }

}