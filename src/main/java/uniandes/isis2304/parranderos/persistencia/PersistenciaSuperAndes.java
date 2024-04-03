package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.Carrito;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.ClienteConsulta;
import uniandes.isis2304.parranderos.negocio.ClientesFrecuentes;
import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.ContieneBodega;
import uniandes.isis2304.parranderos.negocio.ContieneCarrito;
import uniandes.isis2304.parranderos.negocio.ContieneEstante;
import uniandes.isis2304.parranderos.negocio.Estante;
import uniandes.isis2304.parranderos.negocio.Funcionamiento;
import uniandes.isis2304.parranderos.negocio.Pedido;
import uniandes.isis2304.parranderos.negocio.PedidoConsolidado;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.ProductoProveedor;
import uniandes.isis2304.parranderos.negocio.PromPop;
import uniandes.isis2304.parranderos.negocio.Promocion;
import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.negocio.RolUsuario;
import uniandes.isis2304.parranderos.negocio.Sucursal;
import uniandes.isis2304.parranderos.negocio.Usuario;
import uniandes.isis2304.parranderos.negocio.Venta;
import uniandes.isis2304.parranderos.negocio.indiceEstante;
import uniandes.isis2304.parranderos.negocio.resul;
import uniandes.isis2304.parranderos.negocio.resulIndice;
import uniandes.isis2304.parranderos.negocio.resultadosVenta;





public class PersistenciaSuperAndes {
	
	private static Logger log = Logger.getLogger(PersistenciaSuperAndes.class.getName());
	
	public final static String SQL = "javax.jdo.query.SQL";
	
	private static PersistenciaSuperAndes instance;
	
	private PersistenceManagerFactory pmf;
	
	private List <String> tablas;
	
	private SQLUtil sqlUtil;
	
	private SQLCliente sqlCliente;
	
	private SQLBodega sqlBodega;
	
	private SQLCarrito sqlCarrito;
	
	private SQLCategoria sqlCategoria;
	
	private SQLEstante sqlEstante;
	
	private SQLPedido sqlPedido;
	
	private SQLProducto sqlProducto;
	
	private SQLProductoProveedor sqlProductoProveedor;
	
	private SQLPromocion sqlPromocion;
	
	private SQLProveedor sqlProveedor;
	
	private SQLRolUsuario sqlRolUsuario;
	
	private SQLSucursal sqlSucursal;
	
	private SQLUsuario sqlUsuario;
	
	private SQLVenta sqlVenta;
	
	private SQLContieneBodega sqlContieneBodega;
	
	private SQLContieneEstante sqlContieneEstante;
	
	private SQLContieneCarrito sqlContieneCarrito;
	
	private SQLPedidoConsolidado sqlPedidoConsolidado;
	
	private PersistenciaSuperAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("superandesSQ");		
		crearClasesSQL ();
		
		
		tablas = new LinkedList<String> ();
		tablas.add ("superandesSQ");
		tablas.add ("ROL_USUARIO");
		tablas.add ("SUCURSAL");
		tablas.add ("USUARIO");
		tablas.add ("PROVEEDOR");
		tablas.add ("CATEGORIA");
		tablas.add ("PRODUCTO");
		tablas.add ("PRODUCTO_PROVEEDOR");
		tablas.add ("PEDIDO");
		tablas.add ("PROMOCION");
		tablas.add ("CLIENTE");
		tablas.add ("CARRITO");
		tablas.add ("BODEGA");
		tablas.add ("ESTANTE");
		tablas.add ("VENTA");
		tablas.add ("CONTIENE_BODEGA");
		tablas.add ("CONTIENE_ESTANTE");
		tablas.add ("CONTIENE_CARRITO");
		tablas.add("PEDIDO_CONSOLIDADO");

    }
	
	private PersistenciaSuperAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}
	
	public static PersistenciaSuperAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes ();
		}
		return instance;
	}
	
	public static PersistenciaSuperAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes (tableConfig);
		}
		return instance;
	}
	
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	private void crearClasesSQL ()
	{
		sqlCliente = new SQLCliente(this);
	    sqlBodega =  new SQLBodega(this);
		sqlCarrito =  new SQLCarrito(this);
		sqlCategoria =  new SQLCategoria(this);
		sqlEstante =  new SQLEstante(this);
		sqlPedido =  new SQLPedido(this);
		sqlProducto =  new SQLProducto(this);
		sqlProductoProveedor =  new SQLProductoProveedor(this);
		sqlPromocion =  new SQLPromocion(this);
		sqlProveedor =  new SQLProveedor(this);
		sqlRolUsuario =  new SQLRolUsuario(this);
		sqlSucursal =  new SQLSucursal(this);
		sqlUsuario =  new SQLUsuario(this);
		sqlVenta =  new SQLVenta(this);
		sqlUtil = new SQLUtil(this);
		sqlContieneBodega= new SQLContieneBodega(this);
		sqlContieneEstante= new SQLContieneEstante(this);
		sqlContieneCarrito= new SQLContieneCarrito(this);
		sqlPedidoConsolidado= new SQLPedidoConsolidado(this);
	}
	

	public String darSeqSuperAndes ()
	{
		return tablas.get (0);
	}
	
	public String darTablaRolUsuario ()
	{
		return tablas.get (1);
	}
	
	public String darTablaSucursal ()
	{
		return tablas.get (2);
	}
	public String darTablaUsuario ()
	{
		return tablas.get (3);
	}
	public String darTablaProveedor ()
	{
		return tablas.get (4);
	}
	public String darTablaCategoria ()
	{
		return tablas.get (5);
	}
	public String darTablaProducto ()
	{
		return tablas.get (6);
	}
	public String darTablaProductoProveedor ()
	{
		return tablas.get (7);
	}
	public String darTablaPedido ()
	{
		return tablas.get (8);
	}
	public String darTablaPromocion ()
	{
		return tablas.get (9);
	}
	
	public String darTablaCliente ()
	{
		return tablas.get (10);
	}
	public String darTablaCarrito ()
	{
		return tablas.get (11);
	}
	public String darTablaBodega ()
	{
		return tablas.get (12);
	}
	public String darTablaEstante ()
	{
		return tablas.get (13);
	}
	public String darTablaVenta ()
	{
		return tablas.get (14);
	}
	
	public String darTablaContieneBodega ()
	{
		return tablas.get (15);
	}
	
	public String darTablaContieneEstante ()
	{
		return tablas.get (16);
	}
	
	public String darTablaContieneCarrito ()
	{
		return tablas.get (17);
	}
	
	public String darTablaPedidoConsolidado ()
	{
		return tablas.get (18);
	}
	
	
	public List<String> getTablas() {
		return tablas;
	}

	public void setTablas(List<String> tablas) {
		this.tablas = tablas;
	}

	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	

	/* ****************************************************************
	 * 			Métodos para manejar Clientes
	 *****************************************************************/
	public Cliente adicionarCliente(String nombre, String correo, String tipodoc,
			long numerodoc,double puntos, String tipoCliente, String direccion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlCliente.adicionarCliente(pmf.getPersistenceManager(), id, nombre,correo,tipodoc,numerodoc,puntos,
            		tipoCliente,direccion);
            tx.commit();

            log.trace ("Inserción de cliente: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Cliente (id, nombre,correo,tipodoc,numerodoc,puntos,
            		tipoCliente,direccion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public Cliente darClientePorCorreo(String correo) {
    	
    	return (Cliente) sqlCliente.darClientePorCorreo (pmf.getPersistenceManager(), correo);
    	
    }
	
	public Cliente darClientePorId(long id) {
    	
    	return (Cliente) sqlCliente.darClientePorId (pmf.getPersistenceManager(), id);
    	
    }
	
	
	/* ****************************************************************
     *          Métodos para manejar los ROLES DE USUARIO
     *****************************************************************/

    /**
     * Método que inserta, de manera transaccional, una tupla en la tabla RolUsuario
     * Adiciona entradas al log de la aplicación
     * @param nombre - El nombre del tipo de bebida
     * @return El objeto RolUsuario adicionado. null si ocurre alguna Excepción
     */
    public RolUsuario adicionarRolUsuario(String nombre)
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlRolUsuario.adicionarRolUsuario(pm, id, nombre);
            tx.commit();
            
            log.trace ("Inserción de rol de usuario: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new RolUsuario (id, nombre);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }

	
    /* ****************************************************************
     *          Métodos para manejar las SUCURSALES
     *****************************************************************/
    
    public Sucursal adicionarSucursal(String ciudad, String direccion, String nombre) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlSucursal.adicionarSucursal(pm, id, ciudad, direccion, nombre);
            tx.commit();

            log.trace ("Inserción de Sucursal: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Sucursal (id, ciudad, direccion, nombre);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public RolUsuario darRolUsuarioPorId(long id) {
    	
    	return (RolUsuario) sqlRolUsuario.darRolUsuarioPorId (pmf.getPersistenceManager(), id);
    	
    }
	
    /* ****************************************************************
     *          Métodos para manejar los USUARIOS
     *****************************************************************/
    
    public Usuario adicionarUsuario(String nombre, String correo, String contrasena, String tipodoc, long numerodoc, long tipousuario, long sucursal) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlUsuario.adicionarUsuario(pm, id, nombre,contrasena, correo, tipodoc, numerodoc, 
            		tipousuario, sucursal);
            tx.commit();

            log.trace ("Inserción de Usuario: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Usuario (id, nombre, correo,contrasena, tipodoc, numerodoc, tipousuario, sucursal);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public Usuario darUsuarioPorCorreo(String correo) {
    	
    	return (Usuario) sqlUsuario.darUsuarioPorCorreo (pmf.getPersistenceManager(), correo);
    	
    }
	

	/* ****************************************************************
	 * 			Métodos para manejar Promociones
	 *****************************************************************/
	public Promocion adicionarPromocion(long idproducto, long idsucursal, String tipo, long cantidad, Timestamp fechaInicio,
			 Timestamp fechaFin) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlPromocion.adicionarPromocion(pmf.getPersistenceManager(), id, idproducto, idsucursal, tipo, cantidad,
            		fechaInicio, fechaFin);
            tx.commit();

            log.trace ("Inserción de promocion: " + id  + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Promocion (id, idproducto, idsucursal, tipo, cantidad,
            		fechaInicio, fechaFin);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarPromocion (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPromocion.eliminarPromocion (pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public Promocion darPromocionPorProducto (long idproducto) 
   	{
   		return (Promocion) sqlPromocion.darPromocionPorProducto (pmf.getPersistenceManager(), idproducto);
   	}
	
	public long actualizarPromocion (int cantidad,long idpromocion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPromocion.actualizarPromocion (pm, cantidad, idpromocion);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<PromPop> darPromocionesPopulares () 
   	{
   		return (List<PromPop>) sqlPromocion.darPromocionesPopulares (pmf.getPersistenceManager());
   	}
	
	/* ****************************************************************
	 * 			Métodos para manejar Pedido
	 *****************************************************************/
	
	  public Pedido adicionarPedido(long idProveedor, long idProducto, double volumen, double preciosubtotal,long sucursal) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlPedido.adicionarPedido(pmf.getPersistenceManager(), 0,  idProveedor, idProducto, volumen, preciosubtotal,sucursal);
            tx.commit();

            log.trace ("Inserción de pedido "+ ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Pedido (0,  idProveedor,  idProducto,  volumen,  preciosubtotal, sucursal);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	
	public long actualizarPedido (long idpedido, Timestamp fechaEntregada, double calificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPedido.actualizarPedido (pm, idpedido, fechaEntregada,calificacion);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
        if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	
	public List<Pedido> darPedidosPorSucursalYProveedor(long idsucursal, long idproveedor) 
	{
		return (List<Pedido>) sqlPedido.darPedidosPorSucursalYProveedor (pmf.getPersistenceManager(), idsucursal,  idproveedor);
	}
	
	public List<Pedido> darPedidosPorProveedor( long idproveedor) 
	{
		return (List<Pedido>) sqlPedido.darPedidosPorProveedor (pmf.getPersistenceManager(),  idproveedor);
	}
	
	public List<Pedido> darPedidosPorSucursal( long idsucursal) 
	{
		return (List<Pedido>) sqlPedido.darPedidosPorSucursal(pmf.getPersistenceManager(), idsucursal) ;
	}
	
	public Pedido darPedidoPorId (long idpedido) 
   	{
   		return (Pedido) sqlPedido.darPedidoPorId (pmf.getPersistenceManager(), idpedido);
   	}
	
	/* ****************************************************************
	 * 			Métodos para manejar ContieneBodega
	 *****************************************************************/
	
	public long actualizarContieneBodega (long cantidad,long idproducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlContieneBodega.actualizarContieneBodega (pm, cantidad, idproducto);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	public ContieneBodega adicionarContieneBodega(long idproducto,long idbodega,long cantidad) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlContieneBodega.adicionarContieneBodega(pmf.getPersistenceManager(), idproducto,idbodega,cantidad);
            tx.commit();

            log.trace ("Asociacion de producto: "+idproducto+", y bodega: " +idbodega+", "+ tuplasInsertadas + " tuplas insertadas");
            
            return new ContieneBodega (idproducto, idbodega,cantidad);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public ContieneBodega darContieneBodega (long idproducto) 
   	{
   		return (ContieneBodega) sqlContieneBodega.darContieneBodega (pmf.getPersistenceManager(), idproducto);
   	}
	
	/* ****************************************************************
     *          Métodos para manejar los PRODUCTOS
     *****************************************************************/
    
    public Producto adicionarProducto(String nombre, long idCategoria, String marca, double precioUnitario, long cantidad, String unidadMedida, double precioXUndMedida, String presentacion, double peso, double volumen, String codigoDeBarras, long nivelReorden, String subcategoria, Timestamp fechaVencimiento) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlProducto.adicionarProducto(pm, id, nombre, idCategoria, marca, precioUnitario, cantidad, unidadMedida, precioXUndMedida, presentacion, peso, volumen, codigoDeBarras, nivelReorden, subcategoria, fechaVencimiento);
            tx.commit();

            log.trace ("Inserción de Producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Producto (id, nombre, idCategoria, marca, precioUnitario, cantidad, unidadMedida, precioXUndMedida, presentacion, peso, volumen, codigoDeBarras, nivelReorden, subcategoria, fechaVencimiento);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public Producto darProductoPorId (long idproducto) 
	{
		return (Producto) sqlProducto.darProductoPorId (pmf.getPersistenceManager(), idproducto);
	}
    
    public Producto darProductoPorCodigoBarras (String codigo) 
	{
		return (Producto) sqlProducto.darProductoPorCodigoBarras (pmf.getPersistenceManager(), codigo);
	}
    
    public long actualizarProducto (long cantidad,long idproducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProducto.actualizarProducto (pm, cantidad, idproducto);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
    public List<Producto> darProductosCaracteristicas (double precioMin, double precioMax, Timestamp fechaVencimiento,double pesoMin,
			double pesoMax, double volMin, double volMax, long idproveedor, String ciudad, long idsucursal, long idcategoria,
			String subcategoria, Timestamp fechaInVentas, Timestamp fechaFinVentas,int numventas) 
	{
		return sqlProducto.darProductosCaracteristicas (pmf.getPersistenceManager(), precioMin,  precioMax, 
				 fechaVencimiento, pesoMin, pesoMax,  volMin, volMax, idproveedor, 
				ciudad, idsucursal, idcategoria, subcategoria,  fechaInVentas,
				fechaFinVentas,numventas);
	}
    
    
    /* ****************************************************************
	 * 			Métodos para manejar ContieneEstante
	 *****************************************************************/
    public ContieneEstante adicionarContieneEstante(long idproducto,long idestante,long cantidad) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlContieneEstante.adicionarContieneEstante(pmf.getPersistenceManager(), idproducto,idestante,cantidad);
            tx.commit();

            log.trace ("Asociacion de producto: "+idproducto+", y Estante: " +idestante+", "+ tuplasInsertadas + " tuplas insertadas");
            
            return new ContieneEstante (idproducto, idestante,cantidad);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
    public ContieneEstante darContieneEstante (long idproducto) 
   	{
   		return (ContieneEstante) sqlContieneEstante.darContieneEstante (pmf.getPersistenceManager(), idproducto);
   	}
    
    public long actualizarContieneEstante (long cantidad,long idproducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlContieneEstante.actualizarContieneEstante (pm, cantidad, idproducto);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
    /* ****************************************************************
     *          Métodos para manejar las bodegas
     *****************************************************************/
    public Bodega adicionarBodega(long idsucursal, int volumen_max, int peso_max,  long categoria_almac) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
  
        
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlBodega.adicionarBodega(pm, id, idsucursal, volumen_max, peso_max,  categoria_almac);
            tx.commit();

            log.trace ("Inserción de Bodega: " + tuplasInsertadas + " tuplas insertadas");

            return new Bodega (id, idsucursal, volumen_max, peso_max,  categoria_almac);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    public Bodega darBodega (long id) 
   	{
   		return (Bodega) sqlBodega.darBodegaPorId (pmf.getPersistenceManager(), id);
   	}
    
    public List<resulIndice> darIndiceOcupacionBodegasSuc(long idsucursal){
   		return (List<resulIndice>) sqlBodega.darIndiceOcupacionBodegasSuc (pmf.getPersistenceManager(), idsucursal);

    }
    
    public List<resulIndice> darIndiceOcupacionBodegas(){
   		return (List<resulIndice>) sqlBodega.darIndiceOcupacionBodegas (pmf.getPersistenceManager());

    }
    

    

    /* ****************************************************************
     *          Métodos para manejar los ESTANTES
     *****************************************************************/
    
    public Estante adicionarEstante(long idsucursal,  int nivel_abastecimiento, int volumen_max, int peso_max, long categoria_almac) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlEstante.adicionarEstante(pm, id, idsucursal,  nivel_abastecimiento, volumen_max, peso_max, categoria_almac);
            tx.commit();

            log.trace ("Inserción de Estante: " + tuplasInsertadas + " tuplas insertadas");

            return new Estante (id, idsucursal,  nivel_abastecimiento, volumen_max, peso_max, categoria_almac);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public Estante darEstante (long id) 
   	{
   		return (Estante) sqlEstante.darEstantePorId (pmf.getPersistenceManager(), id);
   	}
    
    public List<indiceEstante> darIndiceOcupacionEstantesSuc(long idsucursal){
   		return (List<indiceEstante>) sqlEstante.darIndiceOcupacionEstantesSuc (pmf.getPersistenceManager(), idsucursal);

    }
    
    public List<indiceEstante> darIndiceOcupacionEstantes(){
   		return (List<indiceEstante>) sqlEstante.darIndiceOcupacionEstantes(pmf.getPersistenceManager());

    }
    /* ****************************************************************
     *          Métodos para manejar los PROVEEDORES
     *****************************************************************/
    
    public Proveedor adicionarProveedor(long nit, String nombre, long sucursal) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            
            long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, id, nit, nombre, sucursal);
            tx.commit();

            log.trace ("Inserción de Proveedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Proveedor (id, nit, nombre, sucursal);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    
    
    /* ****************************************************************
     *          Métodos para manejar productos de proveedor
     *****************************************************************/

    public ProductoProveedor darProductoProveedor (long idproducto) 
   	{
   		return (ProductoProveedor) sqlProductoProveedor.darProductosDeProveedorPorId(pmf.getPersistenceManager(), idproducto);
   	}
    
    /* ****************************************************************
	 * 			Métodos para manejar Ventas
	 *****************************************************************/
    public Venta adicionarVenta(long id_cliente, long id_producto, int cantidad,  Timestamp fecha_venta, double total, 
    		long sucursal,long id) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            if(id==0) {id = nextval ();}
            long tuplasInsertadas = sqlVenta.adicionarVenta(pm, id, id_cliente,id_producto,cantidad,fecha_venta,total, sucursal);
            tx.commit();

            log.trace ("Inserción de Venta: " + tuplasInsertadas + " tuplas insertadas");

            return new Venta (id,id_cliente,id_producto,cantidad,fecha_venta,total, sucursal);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public List<Venta> darVentaPorCliente(long idcliente, Timestamp fechaInicio, Timestamp fechaFin)
   	{
   		return (List<Venta>) sqlVenta.darVentaPorCliente(pmf.getPersistenceManager(), idcliente, fechaInicio,fechaFin);
   	}
    
    public List<Venta> darVentaPorClienteYSucursal(long idcliente,long idSucursal,  Timestamp fechaInicio, Timestamp fechaFin)
   	{
   		return (List<Venta>) sqlVenta.darVentaPorClienteYSucursal(pmf.getPersistenceManager(), idcliente,idSucursal, fechaInicio,fechaFin);
   	}
    
    public List<resultadosVenta> darTotalVentasSucursalA (Timestamp fechaIn,Timestamp fechaFn) 
   	{
   		return (List<resultadosVenta>) sqlVenta.darTotalVentasSucursalA(pmf.getPersistenceManager(),fechaIn, fechaFn);
   	}
    public resul darTotalVentasSucursalB (long sucursal,Timestamp fechaIn,Timestamp fechaFn) 
   	{
   		return (resul) sqlVenta.darTotalVentasSucursalB(pmf.getPersistenceManager(),sucursal,fechaIn,fechaFn);
   	}
    
    public List<Venta> darVentasPorFechaGeneral(Timestamp fechain, Timestamp fechafn){
    	return (List<Venta>) sqlVenta.darVentasPorFechaGeneral(pmf.getPersistenceManager(),fechain,fechafn);
    }
    
    public List<Venta> darVentasPorFechaSucursal(Timestamp fechain, Timestamp fechafn,long idsucursal){
    	return (List<Venta>) sqlVenta.darVentasPorFechaSucursal(pmf.getPersistenceManager(),fechain,fechafn,idsucursal);
    }
    /* ****************************************************************
	 * 			Métodos para manejar Carrito
	 *****************************************************************/
    public Carrito adicionarCarrito() 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlCarrito.adicionarCarrito(pm, id);
            tx.commit();

            log.trace ("Inserción de Venta: " + tuplasInsertadas + " tuplas insertadas");

            return new Carrito (id,0);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public long asignarCarrito(long idcarrito,long idcliente) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
           
            long resp = sqlCarrito.asignarCarrito(pm, idcarrito,idcliente);
            tx.commit();

            log.trace ("Asignacion de carrito: " + resp );

            return resp;
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return (Long) null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
    
    public List<Carrito> darCarritosDisponibles () 
   	{
   		return (List<Carrito>) sqlCarrito.darCarritosDisponibles(pmf.getPersistenceManager()) ;
   	}
    
    public long limpiarCarrito (long idcarrito) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlContieneCarrito.limpiarCarrito(pm, idcarrito);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<ContieneCarrito> darProductosCarrito(long idcarrito) {
		
		return (List<ContieneCarrito>) sqlContieneCarrito.darProductosCarrito(pmf.getPersistenceManager(),idcarrito) ;
	}
	
	public Carrito darCarritoCliente(long idcliente) {
		
		return (Carrito) sqlCarrito.darCarritoCliente(pmf.getPersistenceManager(),idcliente) ;
	}
	
	public ContieneCarrito tieneProductoCarrito(long idcarrito,long idproducto) {
		
		return (ContieneCarrito) sqlContieneCarrito.tieneProductoCarrito(pmf.getPersistenceManager(),idcarrito,idproducto) ;
	}
	
	public long adicionarProductoCarrito(long idcarrito,long idproducto, long cantidad) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlContieneCarrito.adicionarProductoCarrito(pm, idcarrito,idproducto,cantidad);
            tx.commit();

            

            return tuplasInsertadas;
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }

	public long incrementarProductoCarrito(long idcarrito, long idproducto, long cantidad) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlContieneCarrito.incrementarProductoCarrito(pm, idcarrito,idproducto,cantidad);
            tx.commit();

            
            return tuplasInsertadas;
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	
	public long quitarProductoCarrito(long idcarrito,long idproducto) 
    {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlContieneCarrito.quitarProductoCarrito(pm, idcarrito,idproducto);
            tx.commit();

            

            return tuplasInsertadas;
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }

	public PedidoConsolidado consolidarPedido(long idproveedor, double preciototal,Timestamp fechaesperada, long sucursal) {
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id=nextval();
            long tuplasInsertadas = sqlPedidoConsolidado.consolidarPedidos(pm, id,idproveedor,preciototal,fechaesperada,sucursal);
            tx.commit();

            

            return new PedidoConsolidado(id,idproveedor,preciototal,fechaesperada,new Timestamp(0),0,"Pendiente",sucursal);
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long asignarPedidoConsolidado( long idsucursal,long idproveedor,long idpedido) {
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            
            long tuplasInsertadas = sqlPedido.asignarPedidoConsolidado(pm, idsucursal,idproveedor,idpedido);
            tx.commit();

            
            return tuplasInsertadas;
        }
        catch (Exception e)
        {
//          e.printStackTrace();
            log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	
	public long actualizarPedidoConsolidado (long idpedido, Timestamp fechaEntregada, double calificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPedidoConsolidado.actualizarPedidoConsolidado (pm, idpedido, fechaEntregada,calificacion);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
        if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Pedido> darPedidosPorId(long idpedido) {
		
		return (List<Pedido>) sqlPedido.darPedidosPorId(pmf.getPersistenceManager(),idpedido) ;
	}
	
	public List<ClientesFrecuentes> clientesFrecuentesGeneral(Timestamp fechain,Timestamp fechafin) {
		
		return (List<ClientesFrecuentes>) sqlVenta.clientesFrecuentesGeneral(pmf.getPersistenceManager(),fechain,fechafin) ;
	}
	
	public List<ClientesFrecuentes> clientesFrecuentesSucursal(Timestamp fechain,Timestamp fechafin,long sucursal) {
		
		return (List<ClientesFrecuentes>) sqlVenta.clientesFrecuentesSucursal(pmf.getPersistenceManager(),fechain,fechafin,sucursal) ;
	}
	
	public Timestamp primeraVenta() {
		return (Timestamp) sqlVenta.primeraVenta(pmf.getPersistenceManager());
	}
	
	
	public Timestamp ultimaVenta() {
		return (Timestamp) sqlVenta.ultimaVenta(pmf.getPersistenceManager());
	}
	
	public PedidoConsolidado darPedidoConsolidadoPorId(long id) {
    	
    	return (PedidoConsolidado) sqlPedidoConsolidado.darPedidoConsolidadoPorId (pmf.getPersistenceManager(), id);
    	
    }
	
	public List<Pedido> darPedidosPorProducto(long idproducto) {
		
		return (List<Pedido>) sqlPedido.darPedidosPorProducto(pmf.getPersistenceManager(),idproducto) ;
	}
	
	public List<Pedido> darPedidos() {
		
		return (List<Pedido>) sqlPedido.darPedidos(pmf.getPersistenceManager()) ;
	}

	public List<Consumo> consultarConsumoAgrupar(long idproducto,Timestamp inicio,Timestamp fin,String order,long sucursal) {
		 
		return (List<Consumo>)sqlVenta.consultarConsumoAgrupar(pmf.getPersistenceManager(),idproducto,inicio,fin,order,sucursal);
	}

	public List<Consumo> consultarConsumo(long idproducto, Timestamp inicio, Timestamp fin, String order,long sucursal) {
		
		return (List<Consumo>)sqlVenta.consultarConsumo(pmf.getPersistenceManager(),idproducto,inicio,fin,order,sucursal);
	}

	public List<Cliente> consultarConsumoV2(long idproducto, Timestamp inicio, Timestamp fin, String order, long sucursal) {
		
		return (List<Cliente>)sqlVenta.consultarConsumoV2(pmf.getPersistenceManager(),idproducto,inicio,fin,order,sucursal);
	}

	public List<Funcionamiento> consultarFuncionamiento() {
		return(List<Funcionamiento>) sqlVenta.consultarFuncionamiento(pmf.getPersistenceManager());
		
	}

	public List<ClienteConsulta> buenosClientes() {
		
		return sqlCliente.buenosClientes(pmf.getPersistenceManager());
	}

    
	

}
