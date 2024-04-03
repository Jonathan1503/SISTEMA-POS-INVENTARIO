
	package uniandes.isis2304.parranderos.persistencia;

	import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Producto;


	class SQLProducto {
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
		public SQLProducto (PersistenciaSuperAndes ps)
		{
			this.ps = ps;
		}
		
		/**
		 * Crea y ejecuta la sentencia SQL para adicionar un PRODUCTO a la base de datos de Superandes
		 * @param pm - El manejador de persistencia
		 * @param id - El identificador del bar
		 * @param nombre - El nombre del bar
		 * @param ciudad - La ciudad del bar
		 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
		 * @param sedes - El número de sedes del bar
		 * @return El número de tuplas insertadas
		 */
		public long adicionarProducto (PersistenceManager pm, long id, String nombre, long idcategoria, String marca, double precioUnitario, long cantidad,
	            String unidadMedida, double precioXUndMedida, String presentacion, double peso, double volumen,
	            String codigoDeBarras, long nivelReorden, String subcategoria, Timestamp fechaVencimiento) 
		{
	        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaProducto () + "(id, nombre, idcategoria, marca, preciounitario, cantidad, unidadmedida, precioxundmedida, presentacion, peso, volumen, codigodebarras, nivelreorden, subcategoria, fechavencimiento) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        q.setParameters(id, nombre, idcategoria, marca, precioUnitario, cantidad, unidadMedida, precioXUndMedida, presentacion, peso, volumen, codigoDeBarras, nivelReorden, subcategoria, fechaVencimiento);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para eliminar PRODUCTOS de la base de datos de Superandes, por su nombre
		 * @param pm - El manejador de persistencia
		 * @param nombre - El nombre del bar
		 * @return EL número de tuplas eliminadas
		 */
		public long eliminarProductosPorNombre (PersistenceManager pm, String nombre)
		{
	        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProducto () + " WHERE nombre = ?");
	        q.setParameters(nombre);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para eliminar UN PRODUCTO de la base de datos de Superandes, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param id - El identificador del bar
		 * @return EL número de tuplas eliminadas
		 */
		public long eliminarProductoPorId (PersistenceManager pm, long id)
		{
	        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProducto () + " WHERE id = ?");
	        q.setParameters(id);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PRODUCTO de la 
		 * base de datos de Superandes, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param id - El identificador del bar
		 * @return El objeto PRODUCTO que tiene el identificador dado
		 */
		public Producto darProductoPorId (PersistenceManager pm, long id) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProducto () + " WHERE id = ?");
			q.setResultClass(Producto.class);
			q.setParameters(id);
			return (Producto) q.executeUnique();
		}
		
		public Producto darProductoPorCodigoBarras (PersistenceManager pm, String codigo) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProducto () + " WHERE CODIGODEBARRAS = ?");
			q.setResultClass(Producto.class);
			q.setParameters(codigo);
			return (Producto) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PRODUCTOS de la 
		 * base de datos de Superandes, por su nombre
		 * @param pm - El manejador de persistencia
		 * @param nombre - El nombre de bar buscado
		 * @return Una lista de objetos PRODUCTO que tienen el nombre dado
		 */
		public List<Producto> darProductosPorNombre (PersistenceManager pm, String nombre) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProducto () + " WHERE nombre = ?");
			q.setResultClass(Producto.class);
			return (List<Producto>) q.executeList();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PRODUCTOS de la 
		 * base de datos de Superandes
		 * @param pm - El manejador de persistencia
		 * @return Una lista de objetos PRODUCTO
		 */
		public List<Producto> darProductos (PersistenceManager pm)
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaProducto ());
			q.setResultClass(Producto.class);
			return (List<Producto>) q.executeList();
		}
		
		public long actualizarProducto(PersistenceManager pm,long cantidad, long idproducto) 
		 {
				 Query q = pm.newQuery(SQL, "UPDATE " + ps.darTablaProducto () + " SET cantidad = cantidad + ?  WHERE id = ?");
			     q.setParameters( cantidad, idproducto);
			     return (long) q.executeUnique();            
		 }
			
		public List<Producto> darProductosCaracteristicas (PersistenceManager pm, double precioMin, double precioMax, 
				Timestamp fechaVencimiento,double pesoMin,double pesoMax, double volMin, double volMax, long idproveedor,
				String ciudad, long idsucursal, long idcategoria,String subcategoria, Timestamp fechaInVentas, Timestamp fechaFinVentas,
				int numventas) 
		{
			
			Query q = pm.newQuery(SQL, ""
					+ "SELECT producto.* "
					+ "FROM (SELECT idproducto, SUM(cantidad) AS cv "
					+ "FROM " + ps.darTablaVenta() + " "
					+ "WHERE fechaventa BETWEEN ? AND ? "
					+ "GROUP BY idproducto) ven, (SELECT DISTINCT CONTIENE_BODEGA.IDPRODUCTO AS IDP,SUCURSAL.ID AS "
					+ "IDSU,SUCURSAL.CIUDAD AS CIUDAD"
					+ " FROM CONTIENE_BODEGA, BODEGA, SUCURSAL "
					+ "WHERE CONTIENE_BODEGA.IDBODEGA = BODEGA.ID AND BODEGA.IDSUCURSAL = SUCURSAL.ID) INFO, producto,producto_proveedor "
					+ "WHERE (producto.id = ven.idproducto) AND (producto.id = producto_proveedor.idproducto) AND (INFO.IDP = producto.id) "
					+ "AND (INFO.IDSU = ? ) AND (INFO.CIUDAD = ? ) AND (preciounitario BETWEEN ? AND ?) AND "
					+ "(FECHAVENCIMIENTO > ? ) AND (producto_proveedor.idproveedor = ? ) "
					+ "AND (PESO BETWEEN ? AND ? ) AND (VOLUMEN BETWEEN ? AND ? ) AND  IDCATEGORIA = ? AND SUBCATEGORIA = ? "
					+ "AND ven.cv > ? ");
			q.setResultClass(Producto.class);
			q.setParameters(fechaInVentas,fechaFinVentas,idsucursal,ciudad,precioMin,precioMax,fechaVencimiento,idproveedor,
					pesoMin,pesoMax,volMin,volMax,idcategoria,subcategoria,numventas);
			return (List<Producto>) q.executeList();
		}
		
		
	}


