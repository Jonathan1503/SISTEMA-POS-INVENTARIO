package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Funcionamiento;
import uniandes.isis2304.parranderos.persistencia.PersistenciaSuperAndes;



public class SuperAndes {
	
	private static Logger log = Logger.getLogger(SuperAndes.class.getName());
	
	private PersistenciaSuperAndes ps;
	
	public SuperAndes() {
		
		ps = PersistenciaSuperAndes.getInstance ();
	}
	
	public SuperAndes (JsonObject tableConfig)
	{
		ps = PersistenciaSuperAndes.getInstance(tableConfig);
	}
	
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia ();
	}
	/* ****************************************************************
	 * 			Clientes
	 *****************************************************************/
	public Cliente adicionarCliente (String nombre, String correo, String tipodoc,long numerodoc,double puntos,
			String tipoCliente, String direccion)
	{
        log.info ("Adicionando Cliente: " + nombre);
        Cliente cliente = ps.adicionarCliente (nombre,correo,tipodoc,numerodoc,puntos,tipoCliente,direccion);		
        log.info ("Adicionando Cliente: " + cliente );
        return cliente;
	}
	
	/* ****************************************************************
     *          Roles Usuario
     *****************************************************************/
    public RolUsuario adicionarRolUsuario (String nombre)
    {
        log.info ("Adicionando RolUsuario: " + nombre);
        RolUsuario rolUsuario = ps.adicionarRolUsuario (nombre);       
        log.info ("Adicionando Cliente: " + rolUsuario );
        return rolUsuario;
    }
	
    /* ****************************************************************
     *          Sucursales
     *****************************************************************/
    public Sucursal adicionarSucursal (String ciudad, String direccion, String nombre)
    {
        log.info ("Adicionando Sucursal: " + nombre);
        Sucursal sucursal = ps.adicionarSucursal (ciudad, direccion, nombre);       
        log.info ("Adicionando Sucursal: " + sucursal );
        return sucursal;
    }
    
    /* ****************************************************************
     *          Usuarios
     *****************************************************************/
    public Usuario adicionarUsuario (String nombre, String correo, String contrasena,String tipodoc, long numerodoc, long tipousuario, long sucursal)
    {
        log.info ("Adicionando Usuario: " + nombre);
        Usuario usuario = ps.adicionarUsuario (nombre, correo, contrasena,tipodoc, numerodoc, tipousuario, sucursal);       
        log.info ("Adicionando Usuario: " + usuario );
        return usuario;
    }
	
	/* ****************************************************************
	 * 			Promociones
	 *****************************************************************/
	public Promocion adicionarPromocion (long idproducto, long idsucursal, String tipo, long cantidad,  Timestamp fechaInicio,
			 Timestamp fechaFin)
	{
        log.info ("Adicionando Promocion: " + tipo+ "del producto: "+idproducto);
        Promocion promocion = ps.adicionarPromocion (idproducto,idsucursal,tipo,cantidad,fechaInicio,fechaFin);		
        log.info ("Adicionando Promocion: " + promocion );
        return promocion;
	}
	
	public List<PromPop> darPromocionesPopulares ()
	{
        log.info ("Consultando promociones");
        List<PromPop> promos = ps.darPromocionesPopulares();
        return promos;
	}
	
	public long eliminarPromocion (long id)
	{
        log.info ("Eliminando promocion: " + id);
        long resp = ps.eliminarPromocion (id);
        log.info ("Eliminando promocion: " + resp + " tuplas eliminadas");
        return resp;
	}
	
   public void actualizarPromocion(int cantidad, long idpromocion) {
	   ps.actualizarPromocion(cantidad, idpromocion);
   }
	
	/* ****************************************************************
     *          Bodegas
     *****************************************************************/
    public Bodega adicionarBodega (long idsucursal, int volumen_max, int peso_max,  long categoria_almac)
    {
        log.info ("Adicionando Bodega");
        Bodega bodega = ps.adicionarBodega (idsucursal, volumen_max, peso_max,  categoria_almac);       
        log.info ("Adicionando Bodega");
        return bodega;
    }
    
    public Bodega adicionarBodegaProducto (long idsucursal, int volumen_max, int peso_max,  long categoria_almac,long idproducto)
    {
        log.info ("Adicionando Bodega");
        Bodega bodega = ps.adicionarBodega (idsucursal, volumen_max, peso_max,  categoria_almac);  
        if(bodega!=null) {
            long idbodega = bodega.getId();
            ps.adicionarContieneBodega(idproducto, idbodega,0);

        }
        log.info ("Adicionando Bodega");
        return bodega;
    }
    
    public List<resulIndice> darIndiceOcupacionBodegasSuc(String correo, String clave){
    
    	Usuario usuario = ps.darUsuarioPorCorreo(correo);
    	
		String con = usuario.getContrasena();
		
		if(!con.equals(clave)) {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		else {
			log.info ("Consultando Bodegas");
			long idrol= usuario.getTipousuario();
			RolUsuario rol= ps.darRolUsuarioPorId(idrol);
		    
			if(rol.getNombre().equals("Gerente de sucursal") ) {
				Long idsucursal= usuario.getSucursal();
				List<resulIndice> bodegas = ps.darIndiceOcupacionBodegasSuc(idsucursal);
				return bodegas;
				
			}
			else if(rol.getNombre().equals("Gerente general")) {
				
				List<resulIndice> bodegas = ps.darIndiceOcupacionBodegas();
				return bodegas;
			}
			else {
				
				log.info ("No esta autorizado proceso cancelado" );
				return null;
			}
			
		}
       
        
    	

    }
    

    
    /* ****************************************************************
     *          Estantes
     *****************************************************************/
    public Estante adicionarEstante (long idsucursal, int nivel_abastecimiento, int volumen_max, int peso_max, long categoria_almac)
    {
        log.info ("Adicionando Estante");
        Estante estante = ps.adicionarEstante (idsucursal,  nivel_abastecimiento, volumen_max, peso_max, categoria_almac);       
        log.info ("Adicionando Estante");
        return estante;
    }
    
    public Estante adicionarEstanteProducto (long idsucursal,int nivel_abastecimiento, int volumen_max, int peso_max,  long categoria_almac,long idproducto)
    {
    	 log.info ("Adicionando Estante");
         Estante estante = ps.adicionarEstante (idsucursal,  nivel_abastecimiento, volumen_max, peso_max, categoria_almac); 
        if(estante!=null) {
            long idestante = estante.getId();
            ps.adicionarContieneEstante(idproducto, idestante,0);

        }
        log.info ("Adicionando Estante");
        return estante;
    }
    
    public List<indiceEstante> darIndiceOcupacionEstantesSuc(String correo, String clave){

    	Usuario usuario = ps.darUsuarioPorCorreo(correo);
    	
		String con = usuario.getContrasena();
		
		if(!con.equals(clave)) {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		else {
			log.info ("Consultando Estantes");
			long idrol= usuario.getTipousuario();
			RolUsuario rol= ps.darRolUsuarioPorId(idrol);
		    
			if(rol.getNombre().equals("Gerente de sucursal") ) {
				Long idsucursal= usuario.getSucursal();
				List<indiceEstante> estantes = ps.darIndiceOcupacionEstantesSuc(idsucursal);
				return estantes;
				
			}
			else if(rol.getNombre().equals("Gerente general")) {
				
				List<indiceEstante> estantes = ps.darIndiceOcupacionEstantes();
				return estantes;
			}
			else {
				
				log.info ("No esta autorizado proceso cancelado" );
				return null;
			}
			
		}
       
    }
    
  
    
    /* ****************************************************************
     *          Proveedores
     *****************************************************************/
    public Proveedor adicionarProveedor (long nit, String nombre, long sucursal)
    {
        log.info ("Adicionando Proveedor: " + nombre);
        Proveedor proveedor = ps.adicionarProveedor (nit, nombre, sucursal);       
        log.info ("Adicionando Proveedor: " + proveedor );
        return proveedor;
    }
    
    /* ****************************************************************
     *          Productos
     *****************************************************************/
    public Producto adicionarProducto (String nombre, long idCategoria, String marca, double precioUnitario, long cantidad, String unidadMedida, double precioXUndMedida, String presentacion, double peso, double volumen, String codigoDeBarras, long nivelReorden, String subcategoria,
    		Timestamp fechaVencimiento, long idsucursal)
    {  
        log.info ("Adicionando Producto: " + nombre);
        Producto producto = ps.adicionarProducto (nombre, idCategoria, marca, precioUnitario, cantidad, unidadMedida, precioXUndMedida, presentacion, peso, volumen, codigoDeBarras, nivelReorden, subcategoria,fechaVencimiento );   
        if(producto != null) {
        	Bodega bodega=ps.adicionarBodega(idsucursal, 10000, 10000, idCategoria);
        	long idb=bodega.getId();
        	Long idp= producto.getId();
        	ps.adicionarContieneBodega(idp, idb,cantidad);
        }
        log.info ("Adicionando Producto: " + producto);
        return producto;
    }
	
	/* ****************************************************************
	 * 			Pedidos
	 *****************************************************************/
	public Pedido adicionarPedido (long idProveedor, long idProducto, double volumen, double preciosubtotal,
			 long sucursal)
	{
        log.info ("Adicionando Pedido: " + idProveedor+", "+idProducto);
        Pedido pedido = ps.adicionarPedido (idProveedor,  idProducto,  volumen,  preciosubtotal,sucursal);		
        log.info ("Adicionando Pedido: " + pedido );
        return pedido;
	}
	
	public long actualizarPedido (long idpedido,  Timestamp fechaEntrega, double calificacion)
	{
        log.info ("Actualizando pedio: " + idpedido);
        long cambios = ps.actualizarPedido (idpedido, fechaEntrega, calificacion);
        return cambios;
	}
	
	/* ****************************************************************
	 * 			ContieneBodega
	 *****************************************************************/
	
	public long actualizarContieneBodega (long cantidad,long idproducto)
	{
        log.info ("Actualizando Contiene bodega: " + idproducto);
        long cambios = ps.actualizarContieneBodega(cantidad, idproducto);
        return cambios;
	}
	
	/* ****************************************************************
	 * 			Adicionales
	 *****************************************************************/
	public long llegadaPedido(long idpedido, Timestamp fechaEntrega, double calificacion) {
		
		
		
		   actualizarPedido(idpedido, fechaEntrega, calificacion);
		   
		   Pedido pedido= ps.darPedidoPorId(idpedido);
		   
		   double volumenPedido =pedido.getVolumen();
		   long idproducto= pedido.getIdproducto();
		  
		   Producto producto = ps.darProductoPorId(idproducto);
		   double volumenProducto=producto.getVolumen();
		   int cantidad= (int) (volumenPedido/volumenProducto);
		  
		   long re=actualizarContieneBodega(cantidad,idproducto);
		   log.info ("Se registro la llegada del pedido: " + idpedido);
		   return re;
		
	}
	
	public long actualizarEstante(long idproducto) {
		ContieneEstante infoestante = ps.darContieneEstante(idproducto);
		long idestante= infoestante.getIdestante();
		Estante estante= ps.darEstante(idestante);
		double volmax = estante.getVolumenMax();
		Producto producto= ps.darProductoPorId(idproducto);
		double volproducto = producto.getVolumen();
		long cantidadmax= (long) (volmax/volproducto);
		long cantidadactual= infoestante.getCantidad();
		long cantidadnecesaria= cantidadmax-cantidadactual;
		
		ContieneBodega infobodega= ps.darContieneBodega(idproducto);
		ps.actualizarContieneBodega(-cantidadnecesaria, idproducto);
		ps.actualizarContieneEstante(cantidadnecesaria, idproducto);
		
		log.info ("Se actualizo el estante: " + idestante+", con el producto: "+idproducto);
		return infobodega.getIdbodega();
		
	}
	
	public String registrarVenta(String codigobarras, int cantidad, String cocliente,long idsucursal, Timestamp fecha) {
		Producto producto= ps.darProductoPorCodigoBarras(codigobarras);
		long cantidadactual= producto.getCantidad();
		
		if(cantidad<cantidadactual) {
			
		long idproducto = producto.getId();
		ps.actualizarProducto(-cantidad, idproducto);
		ContieneBodega infobodega = ps.darContieneBodega(idproducto);
		long idbodega= infobodega.getIdbodega();
		Bodega bodega= ps.darBodega(idbodega);
		double volmax= bodega.getVolumenMax();
		double volactual= (cantidadactual-cantidad)*producto.getVolumen();
		double volpedido= volmax-volactual;
		
		ProductoProveedor pproveedor = ps.darProductoProveedor(idproducto);
		
		long idproveedor= pproveedor.getIdproveedor();
		
		Timestamp fechas = new  Timestamp(0);
		
		if((cantidadactual-cantidad)<= producto.getNivelReorden()) {
			
			adicionarPedido(idproveedor,idproducto,volpedido,0,idsucursal);
		}
		
	    ps.actualizarContieneEstante(-cantidad, idproducto);
	    double precio= producto.getPrecioUnitario()*cantidad;
	    Promocion promocion = ps.darPromocionPorProducto(idproducto);
	    if(promocion != null && promocion.getCantidad()>0) {
	    	String tipo = promocion.getTipo();
	    	if(tipo.contains("descuento")) {
	    		int descuento = Integer.parseInt(tipo.substring( tipo.lastIndexOf("o")+2, tipo.indexOf("%") ));
	    		precio -= ((precio*descuento)/100);
	    		long idpromo = promocion.getId();
	    		actualizarPromocion(-cantidad,idpromo);
	    	}
	    	else if(tipo.contains("pague")){
	    		int pague = Integer.parseInt(tipo.substring(tipo.indexOf(" ")+1),tipo.indexOf("l")-1);
	    		int lleve = Integer.parseInt(tipo.substring(tipo.lastIndexOf(" ")+1));
	    		
	    		int numpromo= cantidad/lleve;
	    		if (numpromo==0) {
	    			
	    		}
	    		else {
	    		int cantidadpromo= numpromo*lleve;
	    		int cantnormal= cantidad-cantidadpromo;
	    		double precionormal= cantnormal*producto.getPrecioUnitario();
	    		double preciopromo= numpromo*pague*producto.getPrecioUnitario();
	    		double preciofinal= precionormal+preciopromo;
	    		precio=preciofinal;
	    		long idpromo = promocion.getId();
	    		actualizarPromocion(-numpromo,idpromo);}
	    		
	    	}
	    	
	    	
	    }
	    Cliente cliente=ps.darClientePorCorreo(cocliente);
	    long idcliente = cliente.getId();
	       
	    Venta venta = ps.adicionarVenta(idcliente,idproducto,cantidad,fecha,precio,idsucursal,0);
	    log.info ("Se registro la venta: "+venta.getId());
    	return "FACTURA SUPERANDES\n-----------------------------------\nFecha: "+fecha+"\nId producto: "+idproducto+"\nNombre producto: "+
    	producto.getNombre()+"\nCantidad: "+cantidad+"\nPrecio total: "+precio;
		}
		
		else {
			log.info ("No se puede hacer la venta por falta de inventario");
			return"";
		}
		
	
		
	}

	public List<Producto> darProductosCaracteristica(double precioMin, double precioMax, Timestamp fechaVencimiento,double pesoMin,
			double pesoMax, double volMin, double volMax, long idproveedor, String ciudad, long idsucursal, long idcategoria,
			String subcategoria, Timestamp fechaInVentas, Timestamp fechaFinVentas,int numventas, String correo, String clave){
		Usuario usuario = ps.darUsuarioPorCorreo(correo);
		String con = usuario.getContrasena();
		if(!con.equals(clave)) {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		else {
			long idrol= usuario.getTipousuario();
			RolUsuario rol= ps.darRolUsuarioPorId(idrol);
			if(rol.getNombre().equals("Gerente de sucursal") ) {
				idsucursal= usuario.getSucursal();
			}
			else if(rol.getNombre().equals("Gerente general") ) {
				idsucursal= idsucursal;
			}
			else {
				log.info ("No esta autorizado proceso cancelado" );
				return null;
			}
				
		log.info ("Dar información de Productos por caracteristicas " );
        List<Producto> productos = ps.darProductosCaracteristicas (precioMin,  precioMax, 
				 fechaVencimiento, pesoMin, pesoMax,  volMin, volMax, idproveedor, 
				ciudad, idsucursal, idcategoria, subcategoria,  fechaInVentas,
				fechaFinVentas,numventas); 
        return productos;}
        
	}

	public List<Pedido> darComprasAProveedores(String correo, String clave,long idproveedor){
		long idsucursal;
		Usuario usuario = ps.darUsuarioPorCorreo(correo);
		String con = usuario.getContrasena();
		if(!con.equals(clave)) {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		
		long idrol= usuario.getTipousuario();
		RolUsuario rol= ps.darRolUsuarioPorId(idrol);
		if(rol.getNombre().equals("Gerente de sucursal")) {
			idsucursal= usuario.getSucursal();
			return ps.darPedidosPorSucursalYProveedor(idsucursal, idproveedor);
		}
		else if(rol.getNombre().equals("Gerente general")) {
			return ps.darPedidosPorProveedor(idproveedor);
		}
		
		else {
			log.info ("No tiene permiso proceso cancelado" );
			return null;
		}
		
	}
	
	public List<Venta> darVentasAClientes(String correo, String clave, long idcliente, Timestamp fechaInicio,  Timestamp fechaFin){
		
		long idsucursal;
		Usuario usuario = ps.darUsuarioPorCorreo(correo);
		String con = usuario.getContrasena();
		if(!con.equals(clave)) {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		
		long idrol= usuario.getTipousuario();
		RolUsuario rol= ps.darRolUsuarioPorId(idrol);
		if(rol.getNombre().equals("Gerente de sucursal")) {
			idsucursal= usuario.getSucursal();
			return ps.darVentaPorClienteYSucursal(idcliente, idsucursal, fechaInicio,fechaFin);
		}
		else if(rol.getNombre().equals("Gerente general")) {
			return ps.darVentaPorCliente(idcliente, fechaInicio,fechaFin);
		}
		
		else {
			log.info ("No tiene permiso proceso cancelado" );
			return null;
		}
	}
	
	public Object darTotalVentasSucursal (String correo, String clave,Timestamp fechaIn,Timestamp fechaFn)
	
	{
		Usuario usuario = ps.darUsuarioPorCorreo(correo);
		String con = usuario.getContrasena();
		
		if(!con.equals(clave)) {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		else {
			long idrol= usuario.getTipousuario();
			log.info ("Consultando total");
			RolUsuario rol= ps.darRolUsuarioPorId(idrol);
			if(rol.getNombre().equals("Gerente de sucursal")) {
				long idsucursal= usuario.getSucursal();
				resul total=ps.darTotalVentasSucursalB(idsucursal, fechaIn, fechaFn);
				return "El total recogido por ventas en la sucursal "+idsucursal+", en los rangos de fecha selecionados es: "+total;
			}
			else if(rol.getNombre().equals("Gerente general")) {
				List<resultadosVenta> lista=ps.darTotalVentasSucursalA(fechaIn, fechaFn);
				return lista;
			}
			else {
				log.info ("No esta autorizado proceso cancelado" );
				return null;
			}
				
		
	}
	}
	
	public long solicitarCarrito(String correo) {
		Cliente cliente = ps.darClientePorCorreo(correo);
		long idcliente= cliente.getId();
		
		List<Carrito> carritos= ps.darCarritosDisponibles();
		
		if(carritos.isEmpty()) {
			
			 Carrito carrito= ps.adicionarCarrito();
			 long idcarrito= carrito.getId();
			 ps.asignarCarrito(idcarrito, idcliente);
			 return idcarrito;
		 }
		 else {
			 
			Carrito carrito = carritos.get(0);
			long idcarrito= carrito.getId();
			limpiarCarrito(idcarrito);
			ps.asignarCarrito(idcarrito, idcliente);
			return idcarrito;
			 
		 }
		
		
	}
	
	public void limpiarCarrito(long idcarrito) {
		List<ContieneCarrito> carproductos= ps.darProductosCarrito(idcarrito);
		if(!carproductos.isEmpty()) {
			
			for(int i=0;i<carproductos.size();i++) {
				ContieneCarrito a =carproductos.get(i);
				long idproducto = a.getIdproducto();
				long cantidad= a.getCantidad();
				ps.actualizarContieneEstante(cantidad, idproducto);
				ps.limpiarCarrito(idcarrito);	
			}
		
		}
	}
	
	public long abandonarCarrito(String correo){
		Cliente cliente = ps.darClientePorCorreo(correo);
		long idcliente= cliente.getId();
		Carrito carrito =ps.darCarritoCliente(idcliente);
		ps.asignarCarrito(carrito.getId(), 0);
		return carrito.getId();
	
	}
	
	public void adicionarProductoCarrito(String correo, String codigobarras,long cantidad) {
		long idcliente =ps.darClientePorCorreo(correo).getId();
		long idcarrito= ps.darCarritoCliente(idcliente).getId();
		long idproducto = ps.darProductoPorCodigoBarras(codigobarras).getId();
		ps.actualizarContieneEstante(-cantidad, idproducto);
		
		ContieneCarrito cc=ps.tieneProductoCarrito(idcarrito,idproducto);
	    
		if(cc==null) {
			
			long res = ps.adicionarProductoCarrito(idcarrito,idproducto,cantidad);
			if(res!=-1) {
				log.info ("Se adiciono el producto al carrito");
			}
			else {
				log.info ("No se pudo adicionar producto");
			}
		}
		else {
			
			long res = ps.incrementarProductoCarrito(idcarrito,idproducto,cantidad);
			if(res!=-1) {
				log.info ("Se adiciono el producto al carrito");
			}
			else {
				log.info ("No se pudo adicionar producto");
			}
		}
	}
	
	public void quitarProductoCarrito(String correo, String codigobarras, long cantidad) {
		long idcliente =ps.darClientePorCorreo(correo).getId();
		long idcarrito= ps.darCarritoCliente(idcliente).getId();
		long idproducto = ps.darProductoPorCodigoBarras(codigobarras).getId();
		if(cantidad==0) {
			long can=ps.tieneProductoCarrito(idcarrito, idproducto).getCantidad();
			ps.quitarProductoCarrito(idcarrito, idproducto);
			ps.actualizarContieneEstante(can, idproducto);
			
		}
		else {
			ps.incrementarProductoCarrito(idcarrito, idproducto, -cantidad);
			ps.actualizarContieneEstante(cantidad, idproducto);
		}
	}
	
	public String pagar(String correo, Timestamp fecha) {
		double preciototal = 0;
		String factura= "FACTURA SUPERANDES\n-----------------------------------\nFecha: "+fecha;
		long idventa=0;
		
		long idcliente=ps.darClientePorCorreo(correo).getId();
		long idcarrito=ps.darCarritoCliente(idcliente).getId();
		List<ContieneCarrito> cc =ps.darProductosCarrito(idcarrito);
		
		for(int i=0;i<cc.size();i++) {
			ContieneCarrito contiene =cc.get(i);
			long cantidad = contiene.getCantidad();
			long idproducto= contiene.getIdproducto();
			long cantidadactual= ps.darProductoPorId(idproducto).getCantidad();
			Producto producto = ps.darProductoPorId(idproducto);
			if(cantidad<cantidadactual) {
				ps.actualizarProducto(-cantidad, idproducto);
				
				ContieneBodega infobodega = ps.darContieneBodega(idproducto);
				long idbodega= infobodega.getIdbodega();
				Bodega bodega= ps.darBodega(idbodega);
				long idsucursal= bodega.getIdsucursal();
				double volmax= bodega.getVolumenMax();
				double volactual= (cantidadactual-cantidad)*producto.getVolumen();
				double volpedido= volmax-volactual;
				ProductoProveedor pproveedor = ps.darProductoProveedor(idproducto);
				long idproveedor= pproveedor.getIdproveedor();
				double stpedido = (volpedido/producto.getVolumen())*pproveedor.getPrecio();
				
				if((cantidadactual-cantidad)<= producto.getNivelReorden()) {
					adicionarPedido(idproveedor,idproducto,volpedido,stpedido,idsucursal);
				}
				
				double totalproducto= producto.getPrecioUnitario()*cantidad;
				Promocion promocion = ps.darPromocionPorProducto(idproducto);
				
					if(promocion != null && promocion.getCantidad()>0) {
				    	String tipo = promocion.getTipo();
				    	if(tipo.contains("descuento")) {
				    		int descuento = Integer.parseInt(tipo.substring( tipo.lastIndexOf("o")+2, tipo.indexOf("%") ));
				    		totalproducto -= ((totalproducto*descuento)/100);
				    		long idpromo = promocion.getId();
				    		actualizarPromocion((int)-cantidad ,idpromo);
				    	}
				    	else if(tipo.contains("pague")){
				    		int pague = Integer.parseInt(tipo.substring(tipo.indexOf(" ")+1),tipo.indexOf("l")-1);
				    		int lleve = Integer.parseInt(tipo.substring(tipo.lastIndexOf(" ")+1));
				    		
				    		int numpromo= (int)cantidad/lleve;
				    		if (numpromo==0) {
				    			
				    		}
				    		else {
				    		int cantidadpromo= numpromo*lleve;
				    		int cantnormal= (int)cantidad-cantidadpromo;
				    		double precionormal= cantnormal*producto.getPrecioUnitario();
				    		double preciopromo= numpromo*pague*producto.getPrecioUnitario();
				    		double preciofinal= precionormal+preciopromo;
				    		totalproducto=preciofinal;
				    		long idpromo = promocion.getId();
				    		actualizarPromocion(-numpromo,idpromo);}
				      }
				    			    	
				    }
					Venta venta = ps.adicionarVenta(idcliente,idproducto,(int)cantidad,fecha,totalproducto,idsucursal,idventa);	
					preciototal+=totalproducto;
					idventa= venta.getId();
					factura += "\nId producto: "+idproducto+"\nNombre producto: "+
					    	producto.getNombre()+"\nCantidad: "+cantidad+"\nSubtotal producto: "+totalproducto;
					
			}
			else {
				log.info ("No se puede hacer la venta de el producto: "+ idproducto+" por falta inventario");
				
			}
		}
		ps.limpiarCarrito(idcarrito);
		abandonarCarrito(correo);
		return factura+"\nPrecio total: "+preciototal;
		
	}
	
	public ArrayList<Long> consolidarPedido(String correo, String clave, Timestamp fechaesperada) {
	      Usuario usuario = ps.darUsuarioPorCorreo(correo);
	      if(usuario.getContrasena().equals(clave)) {
	    	  if(ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre().equals("Gerente de sucursal") ) {
	    		  ArrayList<Long> idspedidos = new ArrayList<Long>();
	    		  long idsucursal=usuario.getSucursal();
	    		  List<Pedido>pdsucur=ps.darPedidosPorSucursal(idsucursal);
	    		  ArrayList<Long> proveedores = new ArrayList<Long>();
	    		  for(int i=0;i<pdsucur.size();i++) {
	    			  long idpvd = pdsucur.get(i).getIdproveedor();
	    			  if(!proveedores.contains(idpvd)) { proveedores.add(idpvd);}
	    		  }
	    		  for(int i=0;i<proveedores.size();i++) {
	    			  double total=0;
	    			  List<Pedido> peds = ps.darPedidosPorSucursalYProveedor(idsucursal, proveedores.get(i));
	    			  System.out.print("cccc");
	    			  for(int j=0;j<peds.size();j++) {
	    				 
	    				  total+=peds.get(j).getPreciosubtotal();
	    			  }
	    			  
	    			  PedidoConsolidado pc =ps.consolidarPedido(proveedores.get(i),total,fechaesperada,idsucursal);
	    			  
	    			  ps.asignarPedidoConsolidado(idsucursal,proveedores.get(i),pc.getIdpedido());
	    			 
	    			  idspedidos.add(pc.getIdpedido()); 
	    			  
	    			  
	    		  }
	    		  log.info ("Se consolidaron los pedidos");
	    		  return idspedidos;
	    	  }
	    	  else {
	    		  log.info ("No esta autorizado proceso cancelado" );
	    		  return null;
	    	  }
	      }
	      else {
	    	  log.info ("Contraseña incorrecta proceso cancelado" );
	    	  return null;
			
	      }
		
		
	}
	
	public long llegadaPedidoConsolidado(String correo, String clave,long idpedido,Timestamp fechaEntrega, double calificacion ) {
		  Usuario usuario =ps.darUsuarioPorCorreo(correo);
		  if(usuario.getContrasena().equals(clave)) {
			  String rol =ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre();
			  if(rol.equals("Operador")) {
				  long idsucursal = usuario.getSucursal();
				  ps.actualizarPedidoConsolidado(idpedido, fechaEntrega, calificacion);
				  List<Pedido> pedidos=ps.darPedidosPorId(idpedido);
				  for(int i=0;i<pedidos.size();i++) {
					  
					  Pedido pedido =pedidos.get(i);
					  long idproducto=pedido.getIdproducto();
					  double vol =pedido.getVolumen();
					  int cantidad= (int) (vol/ps.darProductoPorId(idproducto).getVolumen());
					  ps.actualizarContieneBodega(cantidad, idproducto);
				  }
				  log.info ("Se registro la llegada del pedido: " + idpedido);
				  return idpedido;
			  }
			  else {
				  log.info ("No esta autorizado proceso cancelado" );
				  return -1;
			  }
			  
		  }
		  else {
			  log.info ("Contraseña incorrecta proceso cancelado" );
			  return -1;
		  }
		 
		
	}
	
	public ArrayList<Cliente> clientesFrecuentes(String correo, String clave ) {
		Timestamp fechain =new Timestamp(ps.primeraVenta().getTime()-((long)60*60000));
		Timestamp fechafin =new Timestamp(ps.ultimaVenta().getTime()+((long)60*60000));
		Usuario usuario =ps.darUsuarioPorCorreo(correo);
		if(usuario.getContrasena().equals(clave)) {
			String rol =ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre();
			if(rol.equals("Gerente general")) {
				Timestamp fecha1= fechain;
				Timestamp fecha2= new Timestamp(fecha1.getTime()+((long)30*24*60*60000));
				HashMap<Long,Integer> mapa = new HashMap<>();
				int contador = 0;
				while(fecha2.before(fechafin)) {
					contador+=1;
					List<ClientesFrecuentes> cf=ps.clientesFrecuentesGeneral(fecha1, fecha2);
					for(int i = 0; i<cf.size();i++) {
						 long idcliente = cf.get(i).getIdcliente();
						 if(mapa.containsKey(idcliente)) {
							 mapa.put(idcliente, mapa.get(idcliente)+1); 
						 }
						 else {
							 mapa.put(idcliente, 1);
						 }						 
				   }
				   fecha1=fecha2;
				   fecha2=new Timestamp(fecha1.getTime()+((long)30*24*60*60000));
				}
				ArrayList<Cliente> clientes= new ArrayList<>();
				for(long llave : mapa.keySet()) {
					if(contador==mapa.get(llave)) {
						clientes.add(ps.darClientePorId(llave));
					}
				}
				return clientes;
			}
			else if(rol.equals("Gerente de sucursal")) {
				long idsucursal = usuario.getSucursal();
				Timestamp fecha1= fechain;
				Timestamp fecha2= new Timestamp(fecha1.getTime()+((long)30*24*60*60000));
				HashMap<Long,Integer> mapa = new HashMap<>();
				int contador = 0;
				while(fecha2.before(fechafin)) {
					contador+=1;
					List<ClientesFrecuentes> cf=ps.clientesFrecuentesSucursal(fecha1, fecha2,idsucursal);
					for(int i = 0; i<cf.size();i++) {
						 long idcliente = cf.get(i).getIdcliente();
						 if(mapa.containsKey(idcliente)) {
							 mapa.put(idcliente, mapa.get(idcliente)+1); 
						 }
						 else {
							 mapa.put(idcliente, 1);
						 }						 
				   }
				   fecha1=fecha2;
				   fecha2=new Timestamp(fecha1.getTime()+((long)30*24*60*60000));
				}
				ArrayList<Cliente> clientes= new ArrayList<>();
				for(long llave : mapa.keySet()) {
					if(contador==mapa.get(llave)) {
						clientes.add(ps.darClientePorId(llave));
					}
				}
				return clientes;
			}
			else {
				log.info ("No esta autorizado proceso cancelado" );
				return null;
			}
		}
		else {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		
	}
	
	public ArrayList<Producto>  pocaDemanda(String correo, String clave) {
		ArrayList<Producto> respuesta= new ArrayList<>();
		Usuario usuario =ps.darUsuarioPorCorreo(correo);
		if(usuario.getContrasena().equals(clave)) {
			String rol =ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre();
			if(rol.equals("Gerente general")) {
				List<Pedido>pedidos=ps.darPedidos();
				ArrayList<Long> idproductos= new ArrayList<>();
				for(Pedido p : pedidos) {
					if(!idproductos.contains(p.getIdproducto())) {
						idproductos.add(p.getIdproducto());
					}	
				}
				HashMap<Long,ArrayList<Long>> pedidosfiltrados = new HashMap<>();
				for(long p : idproductos) {
					pedidosfiltrados.put(p, new ArrayList<>());
					int contador= 0;
					for(Pedido pd : pedidos) {if(pd.getIdproducto()==p) {contador+=1;
					ArrayList<Long> lista = pedidosfiltrados.get(p);
					lista.add(pd.getIdpedido());
					pedidosfiltrados.put(p, lista);}}
							
					if(contador==1) {respuesta.add(ps.darProductoPorId(p));}
					else {
						ArrayList<Long> pedidospro= pedidosfiltrados.get(p);
						ArrayList<Timestamp> fechas= new ArrayList<>();
						for(long i : pedidospro) {fechas.add(ps.darPedidoConsolidadoPorId(i).getFechaesperada()); }
						Collections.sort(fechas);
						for(int i=0;i<fechas.size()-1;i++) {
							Timestamp f1= fechas.get(i);
							Timestamp f2 =new Timestamp(f1.getTime()+((long)2*30*24*60*60000));
							if(fechas.get(i+1).after(f2)) {
								respuesta.add(ps.darProductoPorId(p));
							}
						}
						
					}
				}
			}
			else if(rol.equals("Gerente de sucursal")){
				long idsucursal = usuario.getSucursal();
				List<Pedido>pedidos=ps.darPedidosPorSucursal(idsucursal);
				ArrayList<Long> idproductos= new ArrayList<>();
				for(Pedido p : pedidos) {
					if(!idproductos.contains(p.getIdproducto())) {
						idproductos.add(p.getIdproducto());
					}	
				}
				HashMap<Long,ArrayList<Long>> pedidosfiltrados = new HashMap<>();
				for(long p : idproductos) {
					pedidosfiltrados.put(p, new ArrayList<>());
					int contador= 0;
					for(Pedido pd : pedidos) {if(pd.getIdproducto()==p) {contador+=1;
					ArrayList<Long> lista = pedidosfiltrados.get(p);
					lista.add(pd.getIdpedido());
					pedidosfiltrados.put(p, lista);}}
							
					if(contador==1) {respuesta.add(ps.darProductoPorId(p));}
					else {
						ArrayList<Long> pedidospro= pedidosfiltrados.get(p);
						ArrayList<Timestamp> fechas= new ArrayList<>();
						for(long i : pedidospro) {fechas.add(ps.darPedidoConsolidadoPorId(i).getFechaesperada()); }
						Collections.sort(fechas);
						for(int i=0;i<fechas.size()-1;i++) {
							Timestamp f1= fechas.get(i);
							Timestamp f2 =new Timestamp(f1.getTime()+((long)2*30*24*60*60000));
							if(fechas.get(i+1).after(f2)) {
								respuesta.add(ps.darProductoPorId(p));
							}
						}
						
					}
				}
			   
			}
			else {
				log.info ("No esta autorizado proceso cancelado" );
				return null;
			}
			return respuesta;
		}
		else {
			log.info ("Contraseña incorrecta proceso cancelado" );
			return null;
		}
		
	}
	
	public Object analisisOperacion(String correo, String clave, String unidad){
	      Timestamp primera = ps.primeraVenta();
	      Timestamp ultima =ps.ultimaVenta();
	      ArrayList<Object> respuesta= new ArrayList<Object>();
	      
	      Usuario usuario =ps.darUsuarioPorCorreo(correo);
		  if(usuario.getContrasena().equals(clave)) {
			  String rol =ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre();
			  if(rol.equals("Gerente general")) {
					if(unidad.equals("dia")) {
						
						long dias = ((long)(ultima.getTime()-primera.getTime()))/((long)24*60*60000);
						
						
						if(dias<1) {dias=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<dias;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)24*60*60000))-1);
							List<Venta> ventasdia = ps.darVentasPorFechaGeneral(f1, f2);

							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasdia) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							
							}
							
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)24*60*60000));
							
							
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						System.out.print(cantidadproductos);
						System.out.print(ingresos);
					}
					else if (unidad.equals("semana")) {
						
						long semanas = ((long)(ultima.getTime()-primera.getTime()))/((long)7*24*60*60000);
						if(semanas<1) {semanas=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<semanas;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)7*24*60*60000))-1);
							List<Venta> ventasemana = ps.darVentasPorFechaGeneral(f1, f2);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasemana) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)7*24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)7*24*60*60000));
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
					}
					else if (unidad.equals("mes")) {
						
						long meses = ((long)(ultima.getTime()-primera.getTime()))/((long)30*24*60*60000);
						if(meses<1) {meses=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<meses;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)24*60*60000))-1);
							List<Venta> ventasmes = ps.darVentasPorFechaGeneral(f1, f2);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasmes) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)30*24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)30*24*60*60000));
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
					}
					else if(unidad.equals("ano")) {
						long anos = ((long)(ultima.getTime()-primera.getTime()))/((long)365*60*60000);;
						if(anos<1) {anos=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<anos;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)365*60*60000))-1);
							List<Venta> ventasano = ps.darVentasPorFechaGeneral(f1, f2);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasano) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)365*24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)365*24*60*60000));
						}
						
						
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
						
					}
					else {
						log.info ("Unidad no valida proceso cancelado" );
					}
			  }
			  else if(rol.equals("Gerente de sucursal")){
				  long idsucursal=usuario.getSucursal();
				  if(unidad.equals("dia")) {
					  	long dias = ((long)(ultima.getTime()-primera.getTime()))/((long)24*60*60000);
						if(dias<1) {dias=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<dias;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)24*60*60000))-1);
							List<Venta> ventasdia = ps.darVentasPorFechaSucursal(f1, f2,idsucursal);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasdia) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)24*60*60000));
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
					}
					else if (unidad.equals("semana")) {
						
						long semanas = ((long)(ultima.getTime()-primera.getTime()))/((long)7*24*60*60000);
						if(semanas<1) {semanas=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<semanas;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)7*24*60*60000))-1);
							List<Venta> ventasemana = ps.darVentasPorFechaSucursal(f1, f2,idsucursal);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasemana) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)7*24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)7*24*60*60000));
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
					}
					else if (unidad.equals("mes")) {
						
						long meses = ((long)(ultima.getTime()-primera.getTime()))/((long)30*24*60*60000);
						if(meses<1) {meses=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<meses;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)30*24*60*60000))-1);
							List<Venta> ventasmes = ps.darVentasPorFechaSucursal(f1, f2,idsucursal);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasmes) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							f1=new Timestamp(f1.getTime()+((long)30*24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)30*24*60*60000));
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
						
					}
					else if(unidad.equals("ano")) {
						long anos = ((long)(ultima.getTime()-primera.getTime()))/((long)365*24*60*60000);
						if(anos<1) {anos=1;}
						Timestamp f1= primera;
						ArrayList<Long> cantidadproductos = new ArrayList();
						ArrayList<Double> ingresos = new ArrayList();
						for(int i=0; i<anos;i++) {
							Timestamp f2 =new Timestamp((f1.getTime()+((long)365*24*60*60000))-1);
							List<Venta> ventasano = ps.darVentasPorFechaSucursal(f1, f2,idsucursal);
							long pvendidos =0;
							double ingresosven =0;
							ArrayList<Long> productos = new ArrayList();
							for(Venta v : ventasano) {
								ingresosven +=v.getTotal();
								pvendidos+=v.getCantidad();
							}
							cantidadproductos.add(pvendidos);
							ingresos.add(ingresosven);
							
							
							f1=new Timestamp(f1.getTime()+((long)365*24*60*60000));
							f2=new Timestamp(f1.getTime()+((long)365*24*60*60000));
						}
						respuesta.add(cantidadproductos);
						respuesta.add(ingresos);
						
						
						
					}
					else {
						log.info ("Unidad no valida proceso cancelado" );
						return null;
					}
				
				  
				  
			  }
			  else {
				  log.info ("No esta autorizado proceso cancelado" );
				  return null;
			  }
		  }
		  else {
			  log.info ("Contraseña incorrecta proceso cancelado" );
			  return null;
		  }
		  return respuesta;
		
	      
	}
	 public List<Consumo> consultarConsumo(String correo,String clave,long idproducto,Timestamp inicio,Timestamp fin,boolean group,String order) {
		 Usuario usuario =ps.darUsuarioPorCorreo(correo);
			if(usuario.getContrasena().equals(clave)) {
				String rol =ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre();
				if(rol.equals("Gerente general")) {
					if(group) {
						 return ps.consultarConsumoAgrupar(idproducto,inicio,fin,order,0);
					 }
					 else {
						 return ps.consultarConsumo(idproducto, inicio, fin, order,0);
						 
					 }
					
				}
				else if(rol.equals("Gerente de sucursal")) {
					long sucursal=usuario.getSucursal();
					if(group) {
						 return ps.consultarConsumoAgrupar(idproducto,inicio,fin,order,sucursal);
					 }
					 else {
						 return ps.consultarConsumo(idproducto, inicio, fin, order,sucursal);
						 
					 }
					
				}
				else {
					 log.info ("No esta autorizado proceso cancelado" );
					 return null;
				}
			}
			else {
				log.info ("Contraseña incorrecta proceso cancelado" );
				return null;
			}	 
		 
		 
	 }
	 
	 public List<Cliente> consultarConsumoV2(String correo,String clave,long idproducto,Timestamp inicio,Timestamp fin,String order){
		 Usuario usuario =ps.darUsuarioPorCorreo(correo);
			if(usuario.getContrasena().equals(clave)) {
				String rol =ps.darRolUsuarioPorId(usuario.getTipousuario()).getNombre();
				if(rol.equals("Gerente general")) {
					return ps.consultarConsumoV2(idproducto, inicio, fin, order, 0);
				}
				else if(rol.equals("Gerente de sucursal")) {
					long sucursal=usuario.getSucursal();
					return ps.consultarConsumoV2(idproducto, inicio, fin, order, sucursal);
					
				}
				else {
					 log.info ("No esta autorizado proceso cancelado" );
					 return null;
				}
				
			}
			else {
				log.info ("Contraseña incorrecta proceso cancelado" );
				return null;
			}
		
	 }
	 
	 public List<Funcionamiento> consultarFuncionamiento() {
		 return ps.consultarFuncionamiento();
	 }
	 
	 public List<ClienteConsulta>buenosClientes(){
		 return ps.buenosClientes();
	 }
	
	
	
	
	
	
}
