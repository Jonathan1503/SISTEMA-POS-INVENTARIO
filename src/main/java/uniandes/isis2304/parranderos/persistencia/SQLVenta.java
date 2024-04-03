package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.ClientesFrecuentes;
import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Funcionamiento;
import uniandes.isis2304.parranderos.negocio.Venta;
import uniandes.isis2304.parranderos.negocio.resul;
import uniandes.isis2304.parranderos.negocio.resultadosVenta;

class SQLVenta {
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
    public SQLVenta (PersistenciaSuperAndes ps)
    {
        this.ps = ps;
    }
    
    public long adicionarVenta (PersistenceManager pm, long id, long id_cliente, long id_producto, int cantidad,  Timestamp fecha_venta, double total, long sucursal) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaVenta () + "(id, idcliente, idproducto, cantidad, fechaventa, total, sucursal) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, id_cliente, id_producto, cantidad, fecha_venta, total, sucursal);
        return (long) q.executeUnique();
    }
    
    public List<Venta> darVentasPorFechaSucursal (PersistenceManager pm,Timestamp fechaInicio, Timestamp fechaFin, long idsucursal) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaVenta () + " WHERE sucursal = ? AND FECHAVENTA BETWEEN ? AND ? ");
        q.setResultClass(Venta.class);
        q.setParameters(idsucursal ,fechaInicio,fechaFin);
        return (List<Venta>) q.executeList();
    }
    
    public List<Venta> darVentasPorFechaGeneral (PersistenceManager pm, Timestamp fechaInicio, Timestamp fechaFin) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaVenta () + " WHERE FECHAVENTA BETWEEN ? AND ? ");
        q.setResultClass(Venta.class);
        q.setParameters(fechaInicio,fechaFin);
        return (List<Venta>) q.executeList();
    }


    /**
     * Crea y ejecuta la sentencia SQL para eliminar UN VENTA de la base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del estante
     * @return EL número de tuplas eliminadas
     */
    public long eliminarVentaPorId (PersistenceManager pm, long id)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaVenta () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN VENTA de la 
     * base de datos de Superandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param id - El identificador del estante
     * @return El objeto VENTA que tiene el identificador dado
     */
    public Venta darVentaPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaVenta () + " WHERE id = ?");
        q.setResultClass(Venta.class);
        q.setParameters(id);
        return (Venta) q.executeUnique();
    }
    
    public List<Venta> darVentaPorCliente (PersistenceManager pm, long idcliente, Timestamp fechaInicio, Timestamp fechaFin) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaVenta () + " WHERE IDCLIENTE = ? AND FECHAVENTA BETWEEN ? AND ? ");
        q.setResultClass(Venta.class);
        q.setParameters(idcliente ,fechaInicio,fechaFin);
        return (List<Venta>) q.executeList();
    }
    
    public List<Venta> darVentaPorClienteYSucursal (PersistenceManager pm, long idcliente, long idsucursal, Timestamp fechaInicio, Timestamp fechaFin) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaVenta () + " WHERE IDCLIENTE = ? AND SUCURSAL = ? AND FECHAVENTA "
        		+ "BETWEEN ? AND ? ");
        q.setResultClass(Venta.class);
        q.setParameters(idcliente,idsucursal, fechaInicio,fechaFin);
        return (List<Venta>) q.executeList();
    }


    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS VENTAES de la 
     * base de datos de Superandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos VENTA
     */
    public List<Venta> darVentas (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaVenta ());
        q.setResultClass(Venta.class);
        return (List<Venta>) q.executeList();
    }
    
    public List<resultadosVenta> darTotalVentasSucursalA (PersistenceManager pm,Timestamp fechaIn,Timestamp fechaFn )
    {
        Query q = pm.newQuery(SQL, "SELECT SUCURSAL, SUM(TOTAL) as TOTAL FROM " + ps.darTablaVenta () + " WHERE FECHAVENTA >  ? AND FECHAVENTA <   ? GROUP BY SUCURSAL");
        q.setResultClass(Venta.class);
        q.setParameters(fechaIn, fechaFn);
        return (List<resultadosVenta>) q.executeList();
    }
    
    public resul darTotalVentasSucursalB (PersistenceManager pm,long idsucursal,Timestamp fechaIn,Timestamp fechaFn)
    { 
        Query q = pm.newQuery(SQL, "SELECT SUM(TOTAL) as total FROM " + ps.darTablaVenta () + " WHERE FECHAVENTA >   ? AND FECHAVENTA <  ? AND SUCURSAL= ? ");
        
        q.setResultClass(resul.class);
     
        q.setParameters(fechaIn, fechaFn,idsucursal);
        return (resul) q.executeUnique();
    }
    
    public Timestamp primeraVenta(PersistenceManager pm) {
    	Query q = pm.newQuery(SQL, "select MIN(FECHAVENTA) from "+ ps.darTablaVenta());
        q.setResultClass(Timestamp.class);
        return (Timestamp) q.executeUnique();
    }
    
    public Timestamp ultimaVenta(PersistenceManager pm) {
    	Query q = pm.newQuery(SQL, "select MAX(FECHAVENTA) from "+ ps.darTablaVenta());
        q.setResultClass(Timestamp.class);
        return (Timestamp) q.executeUnique();
    }
    
    public List<ClientesFrecuentes> clientesFrecuentesGeneral(PersistenceManager pm, Timestamp fechain, Timestamp fechafin) {
    	 Query q = pm.newQuery(SQL, "select IDCLIENTE,COUNT(*)as veces from venta where fechaventa between ? AND  ? GROUP BY IDCLIENTE ");
         q.setResultClass(ClientesFrecuentes.class);
         q.setParameters(fechain, fechafin);
         return (List<ClientesFrecuentes>) q.executeList();
    }
    
    public List<ClientesFrecuentes> clientesFrecuentesSucursal(PersistenceManager pm, Timestamp fechain, Timestamp fechafin, long idsucursal) {
   	 Query q = pm.newQuery(SQL, "select IDCLIENTE,COUNT(*)as veces from venta where fechaventa between ? AND  ? AND sucursal = ? "
   	 		+ "GROUP BY IDCLIENTE having count(*)>1 ");
        q.setResultClass(ClientesFrecuentes.class);
        q.setParameters(fechain, fechafin,idsucursal);
        return (List<ClientesFrecuentes>) q.executeList();
   }

	public List<Consumo> consultarConsumoAgrupar(PersistenceManager pm,long idproducto,Timestamp inicio, Timestamp fin,String order,long sucursal) {
		String sucur= "";
		if(sucursal!=0) {sucur="and venta.sucursal = "+sucursal;}
		Query q = pm.newQuery(SQL, "select cliente.*, sum(cantidad)as cantidad from venta,cliente "
				+ "where idproducto = ? and venta.idcliente= cliente.id and venta.fechaventa between ? and ? "+sucur
				+ "group by cantidad,idcliente,cliente.id,cliente.nombre,cliente.correo,cliente.tipodoc,cliente.numerodoc,cliente.puntos,cliente.tipocliente,cliente.direccion "
				+ "order by "+order+"");
        q.setResultClass(Consumo.class);
        q.setParameters(idproducto, inicio,fin);
        return (List<Consumo>) q.executeList();
		
	}

	public List<Consumo> consultarConsumo(PersistenceManager pm, long idproducto, Timestamp inicio,	Timestamp fin, String order,long sucursal) {
		String sucur= "";
		if(sucursal!=0) {sucur="and venta.sucursal = "+sucursal;}
		Query q = pm.newQuery(SQL, "select cliente.*, venta.fechaventa,venta.cantidad from venta,cliente "
				+ "where idproducto = ? and venta.idcliente= cliente.id and venta.fechaventa between ? and ? "+sucur+" order by "+order+"");
        q.setResultClass(Consumo.class);
        q.setParameters(idproducto, inicio,fin);
        return (List<Consumo>) q.executeList();
	}

	public List<Cliente> consultarConsumoV2(PersistenceManager pm, long idproducto, Timestamp inicio,Timestamp fin, String order, long sucursal) {
		String sucur= "";
		if(sucursal!=0) {sucur="and venta.sucursal = "+sucursal;}
		Query q = pm.newQuery(SQL, "SELECT CLIENTE.* FROM CLIENTE LEFT JOIN(select  UNIQUE IDCLIENTE FROM VENTA "
				+ "where idproducto = ? and venta.fechaventa between ? and ? "+sucur+" )AUX ON AUX.IDCLIENTE= CLIENTE.ID "
				+ "WHERE AUX.IDCLIENTE IS NULL"
				+ " order by "+order+"");
        q.setResultClass(Cliente.class);
        q.setParameters(idproducto, inicio,fin);
        return (List<Cliente>) q.executeList();
	}
	
	public List<Funcionamiento> consultarFuncionamiento(PersistenceManager pm) {
		
		Query q = pm.newQuery(SQL, "select semana.numero as semana,maxpro.prodmas,maxpro.cpmas,minpro.prodmenos,minpro.cpmenos,maxped.provmas,maxped.cprovmas,minped.provmenos,minped.cprovmenos \r\n"
				+ "from semana full join(\r\n"
				+ "select a.cantidad as cpmas,a.semana,MAX(a.idproducto)prodmas from(select  sum(cantidad)as cantidad,to_number(to_char(CAST(fechaventa AS DATE),'WW'))as semana,idproducto  from venta \r\n"
				+ "group by to_number(to_char(CAST(fechaventa AS DATE),'WW	')),idproducto)a join\r\n"
				+ "(select max(cantidad)as cantidad,semana from(select  sum(cantidad)as cantidad,to_number(to_char(CAST(fechaventa AS DATE),'WW'))as semana,idproducto  from venta \r\n"
				+ "group by to_number(to_char(CAST(fechaventa AS DATE),'WW')),idproducto)\r\n"
				+ "group by semana)aux on aux.semana = a.semana and a.cantidad = aux.cantidad\r\n"
				+ "GROUP BY a.cantidad,a.semana)maxpro on semana.numero= maxpro.semana full join\r\n"
				+ "(select  a.cantidad as cpmenos,a.semana,MAX(a.idproducto) as prodmenos from(select  sum(cantidad)as cantidad,to_number(to_char(CAST(fechaventa AS DATE),'WW'))as semana,idproducto  from venta \r\n"
				+ "group by to_number(to_char(CAST(fechaventa AS DATE),'WW')),idproducto)a join\r\n"
				+ "(select min(cantidad)as cantidad,semana from(select  sum(cantidad)as cantidad,to_number(to_char(CAST(fechaventa AS DATE),'WW'))as semana,idproducto  from venta \r\n"
				+ "group by to_number(to_char(CAST(fechaventa AS DATE),'WW')),idproducto)\r\n"
				+ "group by semana)aux on aux.semana = a.semana and a.cantidad = aux.cantidad\r\n"
				+ "GROUP BY a.cantidad,a.semana)minpro on semana.numero = minpro.semana full join\r\n"
				+ "(select  og.semana,MAX(og.idproveedor)as provmas,og.pedidos as cprovmas from(select to_number(to_char(CAST(fecha AS DATE),'WW'))as semana, idproveedor,count(idproveedor)as pedidos from pedido\r\n"
				+ "group by to_number(to_char(CAST(fecha AS DATE),'WW')),idproveedor)og join\r\n"
				+ "(select max(pedidos)as pedidos,semana from(select to_number(to_char(CAST(fecha AS DATE),'WW'))as semana, idproveedor,count(idproveedor)as pedidos from pedido\r\n"
				+ "group by to_number(to_char(CAST(fecha AS DATE),'WW')),idproveedor)\r\n"
				+ "group by semana) aux on aux.semana= og.semana and aux.pedidos = og.pedidos\r\n"
				+ "GROUP BY og.pedidos,og.semana)maxped on maxped.semana=semana.numero full join \r\n"
				+ "(select og.semana,MAX(og.idproveedor)as provmenos,og.pedidos as cprovmenos from(select to_number(to_char(CAST(fecha AS DATE),'WW'))as semana, idproveedor,count(idproveedor)as pedidos from pedido\r\n"
				+ "group by to_number(to_char(CAST(fecha AS DATE),'WW')),idproveedor)og join\r\n"
				+ "(select min(pedidos)as pedidos,semana from(select to_number(to_char(CAST(fecha AS DATE),'WW'))as semana, idproveedor,count(idproveedor)as pedidos from pedido\r\n"
				+ "group by to_number(to_char(CAST(fecha AS DATE),'WW')),idproveedor)\r\n"
				+ "group by semana) aux on aux.semana= og.semana and aux.pedidos = og.pedidos\r\n"
				+ "GROUP BY og.pedidos,og.semana)minped on minped.semana=semana.numero\r\n"
				+ "");
        q.setResultClass(Funcionamiento.class);
   
        return (List<Funcionamiento>) q.executeList();
	}
}