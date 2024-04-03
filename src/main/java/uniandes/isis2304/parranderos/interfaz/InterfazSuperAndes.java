package uniandes.isis2304.parranderos.interfaz;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.ClienteConsulta;
import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Funcionamiento;
import uniandes.isis2304.parranderos.negocio.Pedido;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.PromPop;
import uniandes.isis2304.parranderos.negocio.Promocion;
import uniandes.isis2304.parranderos.negocio.SuperAndes;
import uniandes.isis2304.parranderos.negocio.VOBodega;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.VOEstante;
import uniandes.isis2304.parranderos.negocio.VOPedido;
import uniandes.isis2304.parranderos.negocio.VOProducto;
import uniandes.isis2304.parranderos.negocio.VOPromocion;
import uniandes.isis2304.parranderos.negocio.VOProveedor;
import uniandes.isis2304.parranderos.negocio.VORolUsuario;
import uniandes.isis2304.parranderos.negocio.VOSucursal;
import uniandes.isis2304.parranderos.negocio.VOUsuario;
import uniandes.isis2304.parranderos.negocio.VOVenta;
import uniandes.isis2304.parranderos.negocio.Venta;
import uniandes.isis2304.parranderos.negocio.indiceEstante;
import uniandes.isis2304.parranderos.negocio.resulIndice;



/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazSuperAndes extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazSuperAndes.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private SuperAndes superAndes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazSuperAndes( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        superAndes = new SuperAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "SuperAndes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    /* ****************************************************************
	 * 			CRUD de Productos
	 *****************************************************************/
    public void adicionarProductos( )
    {
    	try 
    	{
    		String idsucursal = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String nombre = JOptionPane.showInputDialog (this, "Nombre del producto?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String idcategoria = JOptionPane.showInputDialog (this, "Id de la categoria?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String MARCA = JOptionPane.showInputDialog (this, "Nombre de la marca?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String PRECIO_UNITARIO = JOptionPane.showInputDialog (this, "Precio unitario?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String CANTIDAD = JOptionPane.showInputDialog (this, "Cantidad?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String UND_MEDIDA = JOptionPane.showInputDialog (this, "Unidad de medida?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String PRECIO_X_UND_MEDIDA = JOptionPane.showInputDialog (this, "Precio por unidad de medida?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String PRESENTACION = JOptionPane.showInputDialog (this, "Presentacion?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String PESO = JOptionPane.showInputDialog (this, "Peso?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String VOLUMEN = JOptionPane.showInputDialog (this, "Volumen?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String CODIGO_BARRAS = JOptionPane.showInputDialog (this, "Codigo de barras?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String NIVEL_REORDEN = JOptionPane.showInputDialog (this, "Nivel de reorden?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String SUBCATEGORIA = JOptionPane.showInputDialog (this, "sucategoria?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		String FECHA_VENCIMIENTO = JOptionPane.showInputDialog (this, "fecha de vencimiento?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fecha= java.sql.Timestamp.valueOf(FECHA_VENCIMIENTO+" 00:00:00.000");
    		if (nombre != null && idcategoria != null && MARCA != null && PRECIO_UNITARIO != null && CANTIDAD != null && UND_MEDIDA != null && PRECIO_X_UND_MEDIDA != null
    				&& PRESENTACION != null && PESO != null && VOLUMEN != null && CODIGO_BARRAS != null && NIVEL_REORDEN != null && SUBCATEGORIA != null
    				&& FECHA_VENCIMIENTO != null )
    		{
    			
        		VOProducto pr = superAndes.adicionarProducto(nombre, Long.parseLong(idcategoria), MARCA,Double. parseDouble(PRECIO_UNITARIO) ,
        				Long.parseLong(CANTIDAD), UND_MEDIDA, Double. parseDouble(PRECIO_X_UND_MEDIDA), PRESENTACION, Double.parseDouble(PESO),
        				Double. parseDouble(VOLUMEN),CODIGO_BARRAS, Long.parseLong(NIVEL_REORDEN), SUBCATEGORIA,fecha,Long.parseLong(idsucursal));
        		if (pr == null)
        		{
        			throw new Exception ("No se pudo crear el producto: "+nombre);
        		}
        		String resultado = "En adicionar Producto\n\n";
        		resultado += "Tipo de bebida adicionado exitosamente: " + pr;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void darProductosPorCaracteristica( )
    {
    	try 
    	{
    		
			String correo =  JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
    		String clave =  JOptionPane.showInputDialog (this, "Ingrese su clave ", "Adicionar clave", JOptionPane.QUESTION_MESSAGE);
    		double precioMin = Double.parseDouble(JOptionPane.showInputDialog (this, "Precio minimo", "Adicionar", JOptionPane.QUESTION_MESSAGE));    
    		double precioMax = Double.parseDouble(JOptionPane.showInputDialog (this, "Precio maximo", "Adicionar", JOptionPane.QUESTION_MESSAGE)); 
    		String fechaVencimiento = JOptionPane.showInputDialog (this, "fecha de vencimiento?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fecha= java.sql.Timestamp.valueOf(fechaVencimiento+" 00:00:00.000");
    		double pesoMin = Double.parseDouble(JOptionPane.showInputDialog (this, "Peso minimo", "Adicionar", JOptionPane.QUESTION_MESSAGE));    
    		double pesoMax = Double.parseDouble(JOptionPane.showInputDialog (this, "Peso maximo", "Adicionar", JOptionPane.QUESTION_MESSAGE)); 
    		double volMin = Double.parseDouble(JOptionPane.showInputDialog (this, "Volumen minimo", "Adicionar", JOptionPane.QUESTION_MESSAGE));    
    		double volMax = Double.parseDouble(JOptionPane.showInputDialog (this, "Volumen maximo", "Adicionar", JOptionPane.QUESTION_MESSAGE)); 
    		long idproveedor= Long.parseLong(JOptionPane.showInputDialog (this, "Id del proveedor", "Adicionar", JOptionPane.QUESTION_MESSAGE));
    		String ciudad = JOptionPane.showInputDialog (this, "Disponible en que ciudad?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		long idsucursal= Long.parseLong(JOptionPane.showInputDialog (this, "Id de la sucursal en la que se encuentra?", "Adicionar", JOptionPane.QUESTION_MESSAGE));
    		long idcategoria= Long.parseLong(JOptionPane.showInputDialog (this, "Id de la categoria", "Adicionar", JOptionPane.QUESTION_MESSAGE));
    		String subcategoria = JOptionPane.showInputDialog (this, "sucategoria?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		String fechaIVentas = JOptionPane.showInputDialog (this, "Fecha inicio ventas?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fechaInVentas= java.sql.Timestamp.valueOf(fechaIVentas+" 00:00:00.000");
    		String fechaFIVentas = JOptionPane.showInputDialog (this, "Fecha fin ventas?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fechaFinVentas= java.sql.Timestamp.valueOf(fechaFIVentas+" 00:00:00.000");
    		int numventas= Integer.parseInt(JOptionPane.showInputDialog (this, "Cantidad de ventas?", "Adicionar", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		
    		if ( correo!= null && clave!= null && fechaVencimiento!= null && ciudad!= null && subcategoria!= null && fechaIVentas!= null && fechaFIVentas != null)
    				
    		{
    			
        		List<Producto> pr = superAndes.darProductosCaracteristica( precioMin,  precioMax,  fecha, pesoMin,
        				 pesoMax,  volMin,  volMax,  idproveedor,  ciudad,  idsucursal,  idcategoria,
        				 subcategoria,  fechaInVentas,  fechaFinVentas, numventas,  correo,  clave
        				);
        		if (pr == null)
        		{
        			throw new Exception ("No se pudo obetener los productos");
        		}
        		String resultado = "En obtener Producto\n\n";
        		 for(int i=0;i<pr.size();i++) {
                 	resultado += "Producto: "+pr.get(i).getId();
                 }
                 resultado +=  "\n" + pr.toString();
        		
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /* **********************
	 * 			CRUD de RolUsuario
	 ***********************/
    
    public void adicionarRolUsuario( )
    {
    	try 
    	{
    		String nombreRol = JOptionPane.showInputDialog (this, "Nombre del rol?", "Adicionar rol de usuario", JOptionPane.QUESTION_MESSAGE);
    		if (nombreRol != null)
    		{
        		VORolUsuario r = superAndes.adicionarRolUsuario (nombreRol);
        		if (r == null)
        		{
        			throw new Exception ("No se pudo crear un rol de usuario con nombre: " + nombreRol);
        		}
        		String resultado = "En adicionarRolUsuario\n\n";
        		resultado += "Rol de usuario adicionado exitosamente: " + r;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* **********************
	 * 			CRUD de Sucursal
	 ***********************/
    
    public void adicionarSucursal( )
    {
    	try 
    	{
    		String ciudad = JOptionPane.showInputDialog (this, "Nombre de la ciudad?", "Adicionar ciudad", JOptionPane.QUESTION_MESSAGE);
    		String direccion = JOptionPane.showInputDialog (this, "Cual es la direccion?", "Adicionar direccion", JOptionPane.QUESTION_MESSAGE);
    		String nombre = JOptionPane.showInputDialog (this, "Nombre de la sucursal?", "Adicionar nombre", JOptionPane.QUESTION_MESSAGE);
    		if (ciudad != null && direccion != null && nombre != null)
    		{
        		VOSucursal s = superAndes.adicionarSucursal (ciudad, direccion, nombre);
        		if (s == null)
        		{
        			throw new Exception ("No se pudo crear una sucursal con nombre: " + nombre);
        		}
        		String resultado = "En adicionarSucursal\n\n";
        		resultado += "Sucursal adicionada exitosamente: " + s;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void darIndiceOcupacionBodegasSuc( )
    {
        try 
        {
        		String correo =  JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
        		String clave =  JOptionPane.showInputDialog (this, "Ingrese su clave ", "Adicionar clave", JOptionPane.QUESTION_MESSAGE);
            
                List<resulIndice> bod = superAndes.darIndiceOcupacionBodegasSuc(correo,clave);
                
                String resultado = "En darIndiceOcupacionBodegasSuc1\n\n";
                for(int i=0;i<bod.size();i++) {
                	resultado += "Bodega: "+bod.get(i).getIdbodega()+", Indice Ocup: "+bod.get(i).getIndiceocupacion();
                }
                resultado +=  "\n" + bod.toString();
                resultado += "\n OperaciÃ³n terminada";
                panelDatos.actualizarInterfaz(resultado);
            }            
        catch (Exception e) 
        {
//          e.printStackTrace();
            String resultado = generarMensajeError(e);
            panelDatos.actualizarInterfaz(resultado);
        }
    }
    
    public void darIndiceOcupacionEstantesSuc( )
    {
        try 
        {
        		String correo =  JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
        		String clave =  JOptionPane.showInputDialog (this, "Ingrese su clave ", "Adicionar clave", JOptionPane.QUESTION_MESSAGE);
            
        		List<indiceEstante> est = superAndes.darIndiceOcupacionEstantesSuc(correo,clave);
                
                String resultado = "En darIndiceOcupacionBodegasSuc1\n\n";
                for(int i=0;i<est.size();i++) {
                	resultado += "Estante: "+est.get(i).getIdestante()+", Indice Ocup: "+est.get(i).getIndiceocupacion();
                }
                resultado +=  "\n" + est.toString();
                resultado += "\n OperaciÃ³n terminada";
                panelDatos.actualizarInterfaz(resultado);
            }            
        catch (Exception e) 
        {
//          e.printStackTrace();
            String resultado = generarMensajeError(e);
            panelDatos.actualizarInterfaz(resultado);
        }
    }
    
   

    /* **********************
	 * 			CRUD de Usuario
	 ***********************/
    
    public void adicionarUsuario( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre del usuario?", "Adicionar nombre", JOptionPane.QUESTION_MESSAGE);
    		String correo = JOptionPane.showInputDialog (this, "Correo del usuario?", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
    		String contrasena = JOptionPane.showInputDialog (this, "Contrasena?", "Adicionar contrasena", JOptionPane.QUESTION_MESSAGE);
    		String tipodoc = JOptionPane.showInputDialog (this, "Cual es el tipo de documento?", "Adicionar tipo de documento", JOptionPane.QUESTION_MESSAGE);
    		String numdoc = JOptionPane.showInputDialog (this, "Cual es el numero de documento?", "Adicionar numero de documento", JOptionPane.QUESTION_MESSAGE);
    		String tusuario = JOptionPane.showInputDialog (this, "Cual es el tipo de usuario?", "Adicionar tipo de usuario", JOptionPane.QUESTION_MESSAGE);
    		String suc = JOptionPane.showInputDialog (this, "Cual es la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
    		
    		Long numerodoc = Long.parseLong(numdoc);
    		Long tipousuario = Long.parseLong(tusuario);
    		Long sucursal = Long.parseLong(suc);
    		
    		if (nombre != null && correo != null && tipodoc != null && numerodoc != null && tipousuario != null)
    		{
        		VOUsuario u = superAndes.adicionarUsuario (nombre, correo,contrasena, tipodoc, numerodoc, tipousuario, sucursal);
        		if (u == null)
        		{
        			throw new Exception ("No se pudo crear un usuario con nombre: " + nombre);
        		}
        		String resultado = "En adicionarUsuario\n\n";
        		resultado += "Usuario adicionado exitosamente: " + u;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* **********************
   	 * 			CRUD de Cliente
   	 ***********************/
       
       public void adicionarCliente( )
       {
       	try 
       	{
       		String nombre = JOptionPane.showInputDialog (this, "Nombre del cliente?", "Adicionar nombre", JOptionPane.QUESTION_MESSAGE);
       		String correo = JOptionPane.showInputDialog (this, "Correo del cliente?", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
       		
       		String tipodoc = JOptionPane.showInputDialog (this, "Cual es el tipo de documento?", "Adicionar tipo de documento", JOptionPane.QUESTION_MESSAGE);
       		String numdoc = JOptionPane.showInputDialog (this, "Cual es el numero de documento?", "Adicionar numero de documento", JOptionPane.QUESTION_MESSAGE);
       		String tcliente = JOptionPane.showInputDialog (this, "Cual es el tipo de cliente?", "Adicionar tipo de usuario", JOptionPane.QUESTION_MESSAGE);
       		
       		String direccion = JOptionPane.showInputDialog (this, "Cual es la direccion del cliente?", "Adicionar direccion", JOptionPane.QUESTION_MESSAGE);
       		
       		
       		
       		if (nombre != null && correo != null && tipodoc != null &&numdoc != null && tcliente != null&& tcliente != null)
       		{
           		VOCliente u = superAndes.adicionarCliente (nombre, correo, tipodoc, Long.parseLong(numdoc), 0,tcliente, direccion);
           		if (u == null)
           		{
           			throw new Exception ("No se pudo agregar Cliente con nombre: " + nombre);
           		}
           		String resultado = "En adicionarCliente\n\n";
           		resultado += "Cliente adicionado exitosamente: " + u;
       			resultado += "\n OperaciÃ³n terminada";
       			panelDatos.actualizarInterfaz(resultado);
       		}
       		else
       		{
       			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
       		}
   		} 
       	catch (Exception e) 
       	{
//   			e.printStackTrace();
   			String resultado = generarMensajeError(e);
   			panelDatos.actualizarInterfaz(resultado);
   		}
       }

    /* **********************
	 * 			CRUD de Proveedor
	 ***********************/
    
    public void adicionarProveedor( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre de la empresa?", "Adicionar nombre", JOptionPane.QUESTION_MESSAGE);
    		String nit = JOptionPane.showInputDialog (this, "nit de la empresa?", "Adicionar nit", JOptionPane.QUESTION_MESSAGE);
    		String suc = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		
    		if (nombre != null && nit != null && suc != null )
    		{
        		VOProveedor u = superAndes.adicionarProveedor (Long.parseLong(nit),nombre,Long.parseLong(suc));
        		if (u == null)
        		{
        			throw new Exception ("No se pudo crear proveedor con nombre: " + nombre);
        		}
        		String resultado = "En adicionarProveedor\n\n";
        		resultado += "Proveedor adicionado exitosamente: " + u;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* **********************
   	 * 			CRUD de Bodega
   	 ***********************/
       
       public void adicionarBodega( )
       {
       	try 
       	{
       		String sucursal = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
       		String vmax = JOptionPane.showInputDialog (this, "Volumen maximo?", "Adicionar Volumen maximo", JOptionPane.QUESTION_MESSAGE);
       		String pmax = JOptionPane.showInputDialog (this, "Peso maximo?", "Adicionar Peso maximo", JOptionPane.QUESTION_MESSAGE);
       		String categoria = JOptionPane.showInputDialog (this, "Id de la categoria?", "Adicionar categoria", JOptionPane.QUESTION_MESSAGE);
       		if (categoria != null && pmax != null && vmax != null&& categoria != null)
       		{
           		VOBodega s = superAndes.adicionarBodega (Long.parseLong(sucursal),Integer.parseInt(vmax),Integer.parseInt(pmax),Long.parseLong(categoria));
           		if (s == null)
           		{
           			throw new Exception ("No se pudo crear la bodega de la sucursal: "+sucursal);
           		}
           		String resultado = "En adicionarBodega\n\n";
           		resultado += "Bodega adicionada exitosamente: " + s;
       			resultado += "\n OperaciÃ³n terminada";
       			panelDatos.actualizarInterfaz(resultado);
       		}
       		else
       		{
       			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
       		}
   		} 
       	catch (Exception e) 
       	{
//   			e.printStackTrace();
   			String resultado = generarMensajeError(e);
   			panelDatos.actualizarInterfaz(resultado);
   		}
       }
       
       public void adicionarBodegaProducto( )
       {
       	try 
       	{
       		String sucursal = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
       		String vmax = JOptionPane.showInputDialog (this, "Volumen maximo?", "Adicionar Volumen maximo", JOptionPane.QUESTION_MESSAGE);
       		String pmax = JOptionPane.showInputDialog (this, "Peso maximo?", "Adicionar Peso maximo", JOptionPane.QUESTION_MESSAGE);
       		String idproducto = JOptionPane.showInputDialog (this, "Id del producto?", "Adicionar categoria", JOptionPane.QUESTION_MESSAGE);
       		String categoria = JOptionPane.showInputDialog (this, "Id de la categoria?", "Adicionar categoria", JOptionPane.QUESTION_MESSAGE);
       		if (categoria != null && pmax != null && vmax != null&& categoria != null)
       		{
           		VOBodega s = superAndes.adicionarBodegaProducto (Long.parseLong(sucursal),Integer.parseInt(vmax),Integer.parseInt(pmax),Long.parseLong(categoria),
           				Long.parseLong(idproducto));
           		if (s == null)
           		{
           			throw new Exception ("No se pudo crear la bodega de la sucursal: "+sucursal);
           		}
           		String resultado = "En adicionarBodegaProducto\n\n";
           		resultado += "Bodega adicionada exitosamente: " + s;
       			resultado += "\n OperaciÃ³n terminada";
       			panelDatos.actualizarInterfaz(resultado);
       		}
       		else
       		{
       			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
       		}
   		} 
       	catch (Exception e) 
       	{
   			e.printStackTrace();
   			String resultado = generarMensajeError(e);
   			panelDatos.actualizarInterfaz(resultado);
   		}
       }
       
       /* **********************
      	 * 			CRUD de Estante
      	 ***********************/
          
          public void adicionarEstante( )
          {
          	try 
          	{
          		String sucursal = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
          		String nivel = JOptionPane.showInputDialog (this, "Nivel abastecimiento?", "Adicionar Nivel abastecimiento", JOptionPane.QUESTION_MESSAGE);
          		String vmax = JOptionPane.showInputDialog (this, "Volumen maximo?", "Adicionar Volumen maximo", JOptionPane.QUESTION_MESSAGE);
          		String pmax = JOptionPane.showInputDialog (this, "Peso maximo?", "Adicionar Peso maximo", JOptionPane.QUESTION_MESSAGE);
          		String categoria = JOptionPane.showInputDialog (this, "Id de la categoria?", "Adicionar categoria", JOptionPane.QUESTION_MESSAGE);
          		if (categoria != null && pmax != null && vmax != null&& categoria != null)
          		{
              		VOEstante s = superAndes.adicionarEstante (Long.parseLong(sucursal),Integer.parseInt(nivel),Integer.parseInt(vmax),Integer.parseInt(pmax),Long.parseLong(categoria));
              		if (s == null)
              		{
              			throw new Exception ("No se pudo crear el estante de la sucursal: "+sucursal);
              		}
              		String resultado = "En adicionarEstante\n\n";
              		resultado += "Estante adicionado exitosamente: " + s;
          			resultado += "\n OperaciÃ³n terminada";
          			panelDatos.actualizarInterfaz(resultado);
          		}
          		else
          		{
          			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
          		}
      		} 
          	catch (Exception e) 
          	{
//      			e.printStackTrace();
      			String resultado = generarMensajeError(e);
      			panelDatos.actualizarInterfaz(resultado);
      		}
          }
          
          public void adicionarEstanteProducto( )
          {
          	try 
          	{
          		String sucursal = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
          		String nivel = JOptionPane.showInputDialog (this, "Nivel abastecimiento?", "Adicionar Nivel abastecimiento", JOptionPane.QUESTION_MESSAGE);
          		String vmax = JOptionPane.showInputDialog (this, "Volumen maximo?", "Adicionar Volumen maximo", JOptionPane.QUESTION_MESSAGE);
          		String pmax = JOptionPane.showInputDialog (this, "Peso maximo?", "Adicionar Peso maximo", JOptionPane.QUESTION_MESSAGE);
          		String idproducto = JOptionPane.showInputDialog (this, "Id del producto?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
          		String categoria = JOptionPane.showInputDialog (this, "Id de la categoria?", "Adicionar categoria", JOptionPane.QUESTION_MESSAGE);
          		if (categoria != null && pmax != null && vmax != null&& categoria != null)
          		{
              		VOEstante s = superAndes.adicionarEstanteProducto (Long.parseLong(sucursal),Integer.parseInt(nivel),Integer.parseInt(vmax),
              				Integer.parseInt(pmax),Long.parseLong(categoria),Long.parseLong(idproducto));
              		if (s == null)
              		{
              			throw new Exception ("No se pudo crear el estante de la sucursal: "+sucursal);
              		}
              		String resultado = "En adicionarEstanteProducto\n\n";
              		resultado += "Estante adicionado exitosamente: " + s;
          			resultado += "\n OperaciÃ³n terminada";
          			panelDatos.actualizarInterfaz(resultado);
          		}
          		else
          		{
          			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
          		}
      		} 
          	catch (Exception e) 
          	{
//      			e.printStackTrace();
      			String resultado = generarMensajeError(e);
      			panelDatos.actualizarInterfaz(resultado);
      		}
          }
          
          public void aprovisionarEstante( )
          {
          	try 
          	{
          		String idp = JOptionPane.showInputDialog (this, "Id del producto?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
          		
          		
          		if (idp != null )
          		{
              		long s = superAndes.actualizarEstante (Long.parseLong(idp));
              		if (s<0)
              		{
              			throw new Exception ("No se pudo aprovisionar el estante de la sucursal");
              		}
              		String resultado = "En aprovisionarEstante\n\n";
              		resultado += "Estante aprovisionado exitosamente: " + s;
          			resultado += "\n OperaciÃ³n terminada";
          			panelDatos.actualizarInterfaz(resultado);
          		}
          		else
          		{
          			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
          		}
      		} 
          	catch (Exception e) 
          	{
//      			e.printStackTrace();
      			String resultado = generarMensajeError(e);
      			panelDatos.actualizarInterfaz(resultado);
      		}
          }
    
          /* **********************
        	 * 			CRUD de Promocion
        	 ***********************/
            
            public void adicionarPromocion( )
            {
            	try 
            	{
            		String idprod = JOptionPane.showInputDialog (this, "Id del producto?", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
            		String idsuc = JOptionPane.showInputDialog (this, "Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
            		String tipo = JOptionPane.showInputDialog (this, "Cual es el tipo de promocion?", "Adicionar tipo de promocion", JOptionPane.QUESTION_MESSAGE);
            		String cant = JOptionPane.showInputDialog (this, "Cuantos productos estan disponibles para la promocion?", "Adicionar cantidad", JOptionPane.QUESTION_MESSAGE);
            		String fechaIn = JOptionPane.showInputDialog (this, "Cual es la fecha de inicio de la promocion (AAAA-MM-DD)?", "Adicionar fecha de inicio", JOptionPane.QUESTION_MESSAGE);
            		String fechaF = JOptionPane.showInputDialog (this,"Cual es la fecha de fin de la promocion (AAAA-MM-DD)?", "Adicionar fecha de fin", JOptionPane.QUESTION_MESSAGE);
            		
            		Long idproducto = Long.parseLong(idprod);
            		Long idsucursal = Long.parseLong(idsuc);
            		Long cantidad = Long.parseLong(cant);
            		Timestamp fechaInicio = java.sql.Timestamp.valueOf(fechaIn+" 00:00:00.000");
            		Timestamp fechaFin = java.sql.Timestamp.valueOf(fechaF+" 00:00:00.000");

            		if (idprod != null && idsuc != null && tipo != null && cant != null && fechaIn != null && fechaF != null)
            		{
                		VOPromocion p = superAndes.adicionarPromocion (idproducto, idsucursal, tipo, cantidad, fechaInicio, fechaFin);
                		if (p == null)
                		{
                			throw new Exception ("No se pudo crear la promocion");
                		}
                		String resultado = "En adicionarPromocion\n\n";
                		resultado += "Promocion adicionada exitosamente: " + p;
            			resultado += "\n OperaciÃ³n terminada";
            			panelDatos.actualizarInterfaz(resultado);
            		}
            		else
            		{
            			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
            		}
        		} 
            	catch (Exception e) 
            	{
//        			e.printStackTrace();
        			String resultado = generarMensajeError(e);
        			panelDatos.actualizarInterfaz(resultado);
        		}
            }
            public void darPromocionesPopulares( )
            {
                try 
                {
                    
                        List<PromPop> promo = superAndes.darPromocionesPopulares();
                        
                        
                        String resultado = "En darPromocionesPopulares\n\n";
                        for(int i=0;i<promo.size();i++) {
                        	resultado += "Promo: "+promo.get(i).getIdpromocion()+", Vendido: "+promo.get(i).getVendido();
                        }
                        resultado += "\n OperaciÃ³n terminada";
                        panelDatos.actualizarInterfaz(resultado);
                    }            
                catch (Exception e) 
                {
//                  e.printStackTrace();
                    String resultado = generarMensajeError(e);
                    panelDatos.actualizarInterfaz(resultado);
                }
            }
    
            /* **********************
        	 * 			CRUD de Pedido
        	 ***********************/
            
            public void adicionarPedido( )
            {
            	try 
            	{
            	
            		String idprov = JOptionPane.showInputDialog (this, "Id del proveedor?", "Adicionar proveedor", JOptionPane.QUESTION_MESSAGE);
            		String idprod = JOptionPane.showInputDialog (this, "Id del producto", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
            		String vol = JOptionPane.showInputDialog (this, "Volumen del pedido?", "Adicionar volumen", JOptionPane.QUESTION_MESSAGE);
            		String precio = JOptionPane.showInputDialog (this, "Precio del pedido?", "Adicionar cantidad", JOptionPane.QUESTION_MESSAGE);
            		String fesperada = JOptionPane.showInputDialog (this, "Cual es la fecha esperada de entrega? (AAAA-MM-DD)?", "Adicionar fecha espera", JOptionPane.QUESTION_MESSAGE);
            		String sucursal = JOptionPane.showInputDialog (this,"Id de la sucursal?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
            		
            		
            		Timestamp fechae = java.sql.Timestamp.valueOf(fesperada+" 00:00:00.000");
            		

            		if (idprod != null && idprov != null && idprod != null && vol != null && precio != null && fesperada != null && sucursal != null)
            		{
                		VOPedido p = superAndes.adicionarPedido (Long.parseLong(idprov) , Long.parseLong(idprod), Double.parseDouble(vol), 
                				Double.parseDouble(precio) ,Long.parseLong(sucursal));
                		if (p == null)
                		{
                			throw new Exception ("No se pudo crear el pedido");
                		}
                		String resultado = "En adicionarPedido\n\n";
                		resultado += "Pedido adicionado exitosamente: " + p;
            			resultado += "\n OperaciÃ³n terminada";
            			panelDatos.actualizarInterfaz(resultado);
            		}
            		else
            		{
            			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
            		}
        		} 
            	catch (Exception e) 
            	{
//        			e.printStackTrace();
        			String resultado = generarMensajeError(e);
        			panelDatos.actualizarInterfaz(resultado);
        		}
            }
            
            public void llegadaPedido( )
            {
            	try 
            	{
            	
            		String idped = JOptionPane.showInputDialog (this, "Id del pedido?", "Adicionar pedido", JOptionPane.QUESTION_MESSAGE);
            		String calificacion = JOptionPane.showInputDialog (this, "Calificacion del pedido", "Adicionar calificacion", JOptionPane.QUESTION_MESSAGE);
            		String fentrega = JOptionPane.showInputDialog (this, "Fecha de entrega? (AAAA-MM-DD)?", "Adicionar fecha entrega", JOptionPane.QUESTION_MESSAGE);
            		
            		
            		
            		Timestamp fechae = java.sql.Timestamp.valueOf(fentrega+" 00:00:00.000");
            		

            		if (idped != null && fechae != null && calificacion != null)
            		{
                		Long p = superAndes.llegadaPedido (Long.parseLong(idped) , fechae,Double.parseDouble(calificacion));
                		if (p == null)
                		{
                			throw new Exception ("No se pudo actualizar el pedido");
                		}
                		String resultado = "En actualizarPedido\n\n";
                		resultado += "Pedido adicionado exitosamente: " + p;
            			resultado += "\n OperaciÃ³n terminada";
            			panelDatos.actualizarInterfaz(resultado);
            		}
            		else
            		{
            			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
            		}
        		} 
            	catch (Exception e) 
            	{
//        			e.printStackTrace();
        			String resultado = generarMensajeError(e);
        			panelDatos.actualizarInterfaz(resultado);
        		}
            }
            
            public void darComprasAProveedores( )
            {
                try 
                {
                		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
                		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave ", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
                		String idproveedor = JOptionPane.showInputDialog (this, "Ingrese el id del proveedor", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
                		if(correo != null && clave!= null &&idproveedor!= null ) {
                			List<Pedido> pedidos = superAndes.darComprasAProveedores(correo,clave,Long.parseLong(idproveedor));
                            
                            
                            String resultado = "En darComprasAProveedores\n\n";
                            for(int i=0;i<pedidos.size();i++) {
                            	resultado += "Compra: "+pedidos.get(i).getIdpedido()+", "+pedidos.get(i);
                            }
                            resultado += "\n OperaciÃ³n terminada";
                            panelDatos.actualizarInterfaz(resultado);
                		}
                		else {
                			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
                		}
                		
                    }            
                catch (Exception e) 
                {
//                  e.printStackTrace();
                    String resultado = generarMensajeError(e);
                    panelDatos.actualizarInterfaz(resultado);
                }
            }
            
            /* **********************
        	 * 			CRUD de Ventas
        	 ***********************/
            
            public void registrarVenta( )
            {
            	try 
            	{
            	
            		String cbarras = JOptionPane.showInputDialog (this, "Ingrese el codigo de barras", "Adicionar codigo de barras", JOptionPane.QUESTION_MESSAGE);
            		String cantidad = JOptionPane.showInputDialog (this, "Cantidad del producti", "Adicionar cantidad", JOptionPane.QUESTION_MESSAGE);
            		String cocliente = JOptionPane.showInputDialog (this, "Correo del cliente?", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
            		String idsucursal = JOptionPane.showInputDialog (this, "Id sucursal donde se realiza la compra?", "Adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
            		String fecha = JOptionPane.showInputDialog (this, "Fecha de la compra? (AAAA-MM-DD)?", "Adicionar fecha", JOptionPane.QUESTION_MESSAGE);
            		
            		
            		
            		Timestamp fechae = java.sql.Timestamp.valueOf(fecha+" 00:00:00.000");
            		

            		if (cbarras != null && cantidad != null && cocliente != null && idsucursal != null && fecha != null )
            		{
                		String p = superAndes.registrarVenta (cbarras , Integer.parseInt(cantidad),cocliente,Long.parseLong(idsucursal), fechae);
                		if (p == "")
                		{
                			throw new Exception ("No se pudo registrar la venta");
                		}
                		String resultado = "En registrarVenta\n\n";
                		resultado += "Venta registrada exitosamente: " + p;
            			resultado += "\n OperaciÃ³n terminada";
            			panelDatos.actualizarInterfaz(resultado);
            		}
            		else
            		{
            			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
            		}
        		} 
            	catch (Exception e) 
            	{
//        			e.printStackTrace();
        			String resultado = generarMensajeError(e);
        			panelDatos.actualizarInterfaz(resultado);
        		}
            }
            
            public void darTotalVentasSucursal( )
            {
                try 
                {
                	String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave  ", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String fechaIn = JOptionPane.showInputDialog (this, "Fecha inicio? (AAAA-MM-DD)?", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String fechaFn = JOptionPane.showInputDialog (this, "Fecha fin? (AAAA-MM-DD)?", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		Timestamp fechai = java.sql.Timestamp.valueOf(fechaIn+" 00:00:00.000");
            		Timestamp fechaf = java.sql.Timestamp.valueOf(fechaFn+" 00:00:00.000");
            		if (correo != null && clave != null && fechaIn != null && fechaFn != null ) {
            			Object o = superAndes.darTotalVentasSucursal(correo, clave, fechai, fechaf);
            		
            			String resultado = "En darTotalVentasSucursal\n\n";
                        resultado +=  "\n" + o.toString();
                        resultado += "\n OperaciÃ³n terminada";
                        panelDatos.actualizarInterfaz(resultado);
            		}
            		else
            		{
            			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
            		}
                        
                    }            
                catch (Exception e) 
                {
//                  e.printStackTrace();
                    String resultado = generarMensajeError(e);
                    panelDatos.actualizarInterfaz(resultado);
                }
            }
            
            public void darVentasCliente( )
            {
                try 
                {
                	String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave  ", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String fechaIn = JOptionPane.showInputDialog (this, "Fecha inicio? (AAAA-MM-DD)?", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String fechaFn = JOptionPane.showInputDialog (this, "Fecha fin? (AAAA-MM-DD)?", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		String idcliente= JOptionPane.showInputDialog (this, "Id cliente?", "Ventas", JOptionPane.QUESTION_MESSAGE);
            		Timestamp fechai = java.sql.Timestamp.valueOf(fechaIn+" 00:00:00.000");
            		Timestamp fechaf = java.sql.Timestamp.valueOf(fechaFn+" 00:00:00.000");
            		if (correo != null && clave != null && fechaIn != null && fechaFn != null && idcliente != null ) {
            			List<Venta> ventas = superAndes.darVentasAClientes(correo, clave, Long.parseLong(idcliente),fechai, fechaf);
            			
            			String resultado = "En darVentasCliente\n\n";
            			for(int i=0;i<ventas.size();i++) {
                        	resultado += "Venta: "+ventas.get(i).getId()+", "+ventas.get(i);
                        }
                        resultado += "\n OperaciÃ³n terminada";
                        panelDatos.actualizarInterfaz(resultado);
                        
            		}
            		else
            		{
            			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
            		}
                        
                    }            
                catch (Exception e) 
                {
//                  e.printStackTrace();
                    String resultado = generarMensajeError(e);
                    panelDatos.actualizarInterfaz(resultado);
                }
            }
   
/* ****************************************************************
 *  				CRUD Carrito
 ******************************************************************/
            
    public void solicitarCarrito( )
    {
        try 
        {
        		String correo =  JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
        		
            
                long idcarrito = superAndes.solicitarCarrito(correo);
               
                String resultado = "En darIndiceOcupacionBodegasSuc1\n\n";
                resultado += "Se le asigno el carrito con id: "+idcarrito;
                resultado += "\n OperaciÃ³n terminada";
                panelDatos.actualizarInterfaz(resultado);
                
            }            
        catch (Exception e) 
        {
//                  e.printStackTrace();
            String resultado = generarMensajeError(e);
            panelDatos.actualizarInterfaz(resultado);
        }
    }

    public void devolverProductosCarrito( )
    {
        try 
        {
        		String sidcarrito =  JOptionPane.showInputDialog (this, "Ingrese el id del carrito ", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
                superAndes.limpiarCarrito(Long.parseLong(sidcarrito));
               
                String resultado = "El carrito de id: "+sidcarrito+" ha sido limpiado";
                resultado += "\n OperaciÃ³n terminada";
                panelDatos.actualizarInterfaz(resultado);
                
            }            
        catch (Exception e) 
        {
//                  e.printStackTrace();
            String resultado = generarMensajeError(e);
            panelDatos.actualizarInterfaz(resultado);
        }
    }
    
    public void abandonarCarrito( )
    {
        try 
        {
        		String correo =  JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
                long idcarrito=superAndes.abandonarCarrito(correo);
               
                String resultado = "El carrito de id: "+idcarrito+" ha sido abandonado";
                resultado += "\n OperaciÃ³n terminada";
                panelDatos.actualizarInterfaz(resultado);
                
            }            
        catch (Exception e) 
        {
//                  e.printStackTrace();
            String resultado = generarMensajeError(e);
            panelDatos.actualizarInterfaz(resultado);
        }
    }
    
    public void adicionarProductoCarrito( )
    {
    	try 
    	{
    	
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		String codigobarras = JOptionPane.showInputDialog (this, "Ingrese el codigo de barras del producto", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String cantidad = JOptionPane.showInputDialog (this, "Cantidad del producto que va a agregar", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		   		    		
    		if (correo != null && codigobarras != null && cantidad != null )
    		{
        		superAndes.adicionarProductoCarrito(correo,codigobarras,Long.parseLong(cantidad));
        		
        		String resultado = "En adicionarProductoCarrito\n\n";
        		resultado += "Producto adicionado en el carrito exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void quitarProductoCarrito( )
    {
    	try 
    	{
    	
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo ", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		String codigobarras = JOptionPane.showInputDialog (this, "Ingrese el codigo de barras del producto", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String cantidad = JOptionPane.showInputDialog (this, "Unidades del producto que desea quitar (Si desea quitar todas las unidades del producto ingrese 0)", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		   		    		
    		if (correo != null && codigobarras != null && cantidad != null )
    		{
        		superAndes.quitarProductoCarrito(correo,codigobarras,Long.parseLong(cantidad));
        		
        		String resultado = "En quitarProductoCarrito\n\n";
        		resultado += "Producto quitado del carrito exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void pagar( )
    {
    	try 
    	{
    	
    		
    		String cocliente = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar correo", JOptionPane.QUESTION_MESSAGE);
    		String fecha = JOptionPane.showInputDialog (this, "Fecha de la compra? (AAAA-MM-DD)?", "Adicionar fecha", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		
    		Timestamp fechae = java.sql.Timestamp.valueOf(fecha+" 00:00:00.000");
    		

    		if ( cocliente != null && fecha != null )
    		{
        		String p = superAndes.pagar (cocliente, fechae);
        		if (p == "")
        		{
        			throw new Exception ("No se pudo registrar la venta");
        		}
        		String resultado = "En pagar\n\n";
        		resultado += "Venta registrada exitosamente: " + p;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void llegadaPedidoConsolidado( )
    {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String idped = JOptionPane.showInputDialog (this, "Id del pedido?", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String fentrega = JOptionPane.showInputDialog (this, "Fecha de entrega? (AAAA-MM-DD)?", "Adicionar fecha entrega", JOptionPane.QUESTION_MESSAGE);
    		String calificacion = JOptionPane.showInputDialog (this, "Calificacion del pedido", "Adicionar calificacion", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		Timestamp fechae = java.sql.Timestamp.valueOf(fentrega+" 00:00:00.000");
    		

    		if (idped != null && fechae != null && calificacion != null && correo != null && clave != null )
    		{
        		Long p = superAndes.llegadaPedidoConsolidado (correo,clave,Long.parseLong(idped) , fechae,Double.parseDouble(calificacion));
        		if (p == null)
        		{
        			throw new Exception ("No se pudo actualizar el pedido");
        		}
        		String resultado = "En llegadaPedidoConsolidado\n\n";
        		resultado += "Pedido consolidado actualizado exitosamente: " + p;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void consolidarPedido( )
    {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String fentrega = JOptionPane.showInputDialog (this, "Fecha esperada de entrega? (AAAA-MM-DD)?", "Adicionar fecha entrega", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fechae = java.sql.Timestamp.valueOf(fentrega+" 00:00:00.000");
    		

    		if (fechae != null &&  correo != null && clave != null )
    		{
    			ArrayList<Long> p = superAndes.consolidarPedido (correo,clave,fechae);
        		if (p == null)
        		{
        			throw new Exception ("No se pudo actualizar el pedido");
        		}
        		String resultado = "En consolidarPedido\n\n";
        		resultado += "Pedidos consolidados exitosamente: " + p;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void clientesFrecuentes( )
    {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    				

    		if (correo != null && clave != null )
    		{
    			ArrayList<Cliente> p = superAndes.clientesFrecuentes (correo,clave);
        		if (p == null)
        		{
        			throw new Exception ("No se pudo obtener los clientes frecuentes");
        		}
        		String resultado = "En clientesFrecuentes\n\n";
        		for(int i=0;i<p.size();i++) {
                	resultado += "Cliente: "+p.get(i).getId()+", "+p.get(i);
                }
        		
        		resultado += "Clientes frecuentes ejecutado exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void pocaDemanda( )
    {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    				

    		if (correo != null && clave != null )
    		{
    			ArrayList<Producto> p = superAndes.pocaDemanda(correo,clave);
        		if (p == null)
        		{
        			throw new Exception ("No se pudo obtener los productos con poca demanda");
        		}
        		String resultado = "En pocaDemanda\n\n";
        		for(int i=0;i<p.size();i++) {
                	resultado += "Producto: "+p.get(i).getId()+", "+p.get(i);
                }
        		
        		resultado += "Productos con poca demanda ejecutado exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void analisisOperacion( )
    {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String unidad= JOptionPane.showInputDialog (this, "Unidad de consulta(dia,semana,mes,ano)", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    				

    		if (correo != null && clave != null&& unidad != null )
    		{
    			List<Object> respuesta= (List<Object>) superAndes.analisisOperacion(correo,clave,unidad);
        		if (respuesta==null)
        		{
        			throw new Exception ("No se pudo hacer analisis");
        		}
        		String resultado = "En analisisOperacion\n\n";
        		resultado+="Fechas de mayor demanda (top5):\n";
        		
        		ArrayList<Long> demanda1=(ArrayList<Long>) respuesta.get(0);
        		ArrayList<Long> demanda2 = new ArrayList<>(demanda1);
        		int top=5;
        		if(demanda1.size()<top) {top=demanda1.size();};
        		for(int i=0;i<top;i++) {
        			Long max =Collections.max(demanda1);
            		int maxindex=demanda1.indexOf(max);
            		resultado+=(i+1)+"."+unidad+" "+(maxindex+1)+"|Productos vendidos: "+max+"\n";
            		demanda1.add(maxindex, (long) -1);
            		demanda1.remove(maxindex+1);
            		
        		}
        		resultado+="Fechas de menor demanda (top5):\n";
        		
        		int top2=5;
        		if(demanda2.size()<top2) {top2=demanda2.size();};
        		for(int i=0;i<top2;i++) {
        			
        			Long min =Collections.min(demanda2);
            		int minindex=demanda2.indexOf(min);
            		resultado+=(i+1)+"."+unidad+" "+(minindex+1)+"|Productos vendidos: "+min+"\n";
            		demanda2.add(minindex, Long.MAX_VALUE);
            		demanda2.remove(minindex+1);
            		
        		}
        		ArrayList<Double> ingresos=(ArrayList<Double>) respuesta.get(1);
        		resultado+="Fechas de mayor ingreso (top5):\n";
        		int top3=5;
        		if(ingresos.size()<top3) {top3=ingresos.size();};
        		for(int i=0;i<top3;i++) {
        			Double max =Collections.max(ingresos);
            		int maxindex=ingresos.indexOf(max);
            		resultado+=(i+1)+"."+unidad+" "+(maxindex+1)+"|Ingresos recibidos: "+max+"\n";
            		ingresos.add(maxindex, (double) -1);
            		ingresos.remove(maxindex+1);
            		
        		}
        		
        		
        		
        		
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			//e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void consultarConsumo() {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String idproducto = JOptionPane.showInputDialog (this, "Ingrese el id del producto sobre el que desea consultar", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String finicio = JOptionPane.showInputDialog (this, "Fecha de inicio? (AAAA-MM-DD)?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fi = java.sql.Timestamp.valueOf(finicio+" 00:00:00.000");
    		String ffin = JOptionPane.showInputDialog (this, "Fecha fin? (AAAA-MM-DD)?", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		Timestamp ffn = java.sql.Timestamp.valueOf(ffin+" 00:00:00.000");
    		String agrupar = JOptionPane.showInputDialog (this, "Desea agrupar por cliente? (si o no)", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		boolean ag= false;
    		if(agrupar.equals("si")) {ag=true;}
    		String ordenar= JOptionPane.showInputDialog (this, "Porque parametro desea ordenar (nombre,correo,numero de documento,direccion,cantidad,fecha)", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		if(ordenar.equals("numero de documento")) {
    			ordenar="cliente.numerodoc";
    		}
    		else if(ordenar.equals("fecha")) {
    			ordenar="fechaventa";
    		}
    		else if (!ordenar.equals("cantidad")) {
    			ordenar="cliente."+ordenar;
    		}
    		
    		
    				

    		if (correo != null && clave != null && idproducto!=null && agrupar != null && ordenar !=null  && finicio !=null && ffin!=null )
    		{
    			List<Consumo> c = superAndes.consultarConsumo(correo, clave, Long.parseLong(idproducto), fi, ffn,ag , ordenar);
        		if (c == null)
        		{
        			throw new Exception ("No se pudo obtener la consulta de consumo");
        		}
        		String resultado = "En consultarConsumo\n\n";
        		
        		for(Consumo co:c) {
        			String fv=co.getFechaventa().toString();
        			if(co.getFechaventa().before(java.sql.Timestamp.valueOf("1990-01-01 00:00:00.000"))) {fv="No aplica";}
        			resultado+="Id cliente: "+co.getId()+"|Nombre: "+co.getNombre()+"|Correo: "+ co.getCorreo()+"|Dirección: "+co.getDireccion()+
        			"|Tipo de documento: "+co.getTipodoc()+"|Numero de documento: "+ co.getNumerodoc()+"|Tipo de cliente: "+co.getTipoCliente()+
        			"|Puntos: "+co.getPuntos()+ "|Fecha de venta: "+ fv+"|Cantidad comprada: "+ co.getCantidad()+"\n";        			        			        		        		   
        		}
        		resultado += "Consulta consumo ejecutada exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void consultarConsumoV2() {
    	try 
    	{
    	
    		
    		String correo = JOptionPane.showInputDialog (this, "Ingrese su correo", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String clave = JOptionPane.showInputDialog (this, "Ingrese su clave", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String idproducto = JOptionPane.showInputDialog (this, "Ingrese el id del producto sobre el que desea consultar", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		String finicio = JOptionPane.showInputDialog (this, "Fecha de inicio? (AAAA-MM-DD)?", "Adicionar", JOptionPane.QUESTION_MESSAGE);
    		Timestamp fi = java.sql.Timestamp.valueOf(finicio+" 00:00:00.000");
    		String ffin = JOptionPane.showInputDialog (this, "Fecha fin? (AAAA-MM-DD)?", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		Timestamp ffn = java.sql.Timestamp.valueOf(ffin+" 00:00:00.000");
    		String ordenar= JOptionPane.showInputDialog (this, "Porque parametro desea ordenar (nombre,correo,numero de documento,direccion)", "Adicionar ", JOptionPane.QUESTION_MESSAGE);
    		if(ordenar.equals("numero de documento")) {
    			ordenar="cliente.numerodoc";
    		}
    		
    		else {
    			ordenar="cliente."+ordenar;
    		}
    		
    		
    				

    		if (correo != null && clave != null && idproducto!=null && ordenar !=null  && finicio !=null && ffin!=null )
    		{
    			List<Cliente> c = superAndes.consultarConsumoV2(correo, clave, Long.parseLong(idproducto), fi, ffn, ordenar);
        		if (c == null)
        		{
        			throw new Exception ("No se pudo obtener la consulta de consumo");
        		}
        		String resultado = "En consultarConsumoV2\n\n";
        		
        		for(Cliente co:c) {
        			resultado+="Id cliente: "+co.getId()+"|Nombre: "+co.getNombre()+"|Correo: "+ co.getCorreo()+"|Dirección: "+co.getDireccion()+
        			"|Tipo de documento: "+co.getTipodoc()+"|Numero de documento: "+ co.getNumerodoc()+"|Tipo de cliente: "+co.getTipoCliente()+
        			"|Puntos: "+co.getPuntos()+"\n";        			        			        		        		   
        		}
        		resultado += "Consulta consumo ejecutada exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void consultarFuncionamiento() {
    	try 
    	{
    	
    			List<Funcionamiento> c = superAndes.consultarFuncionamiento();
        		if (c == null)
        		{
        			throw new Exception ("No se pudo obtener la consulta de funcionamiento");
        		}
        		String resultado = "En consultarFuncionamiento\n\n";
        		
        		for(Funcionamiento co:c) {
        			resultado+="Semana: "+co.getSemana()+"|Id Producto mas vendido: "+co.getProdmas()+"|Cantidad vendida: "+ co.getCpmas()+"|Id Producto menos vendido: "+co.getProdmenos()+
        			"|Cantidad vendida: "+co.getCpmenos()+"|Proveedor mas solicitado: "+ co.getProvmas()+"|Pedidos solicitados: "+co.getCprovmas()+
        			"|Proveedor menos solicitado: "+co.getProdmenos()+"|Pedidos solicitados: "+co.getCprovmenos()+"\n";        			        			        		        		   
        		}
        		resultado += "Consulta funcionamiento ejecutada exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		
    		
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void buenosClientes() {
    	try 
    	{
    	
    			List<ClienteConsulta> c = superAndes.buenosClientes();
        		if (c == null)
        		{
        			throw new Exception ("No se pudo obtener los buenos clientes");
        		}
        		String resultado = "En buenosClientes\n\n";
        		
        		for(ClienteConsulta co:c) {
        			resultado +=c.toString()+"\n";		        			        		        		   
        		}
        		resultado += "Consulta buenos clientes ejecutada exitosamente " ;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		
    		
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    
    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazSuperAndes.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazSuperAndes interfaz = new InterfazSuperAndes( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
