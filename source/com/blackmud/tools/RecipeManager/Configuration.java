// ----------------------------------------------------------------------
// Copyright � 2004 Northrop Grumman Corporation -- All Rights Reserved
//
// This material may be reproduced by or for the U.S. Government pursuant
// to the copyright license under the clause at Defense Federal
// Acquisition Regulation Supplement (DFARS) 252.227-7014 (June 1995).
// ----------------------------------------------------------------------
package com.blackmud.tools.RecipeManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * The Class Configuration.
 */
public class Configuration{
	
    /** The prop file name. */
    private String propFileName;
    
    /** The base path. */
    private String basePath;
    
    /** The pkg. */
    private String pkg;
    
    /** The props. */
    private Properties props;
    
    /** The buff stream. */
    private BufferedInputStream buffStream; // kind of a hack for the logging crap
	
	/** The prop prefix. */
	private String propPrefix;
    
    /** The Constant DEFAULT_PROPS_PKG. */
    public static final String DEFAULT_PROPS_PKG ="com/northgrum/properties";
    
    /** The Constant PROP_SUFFIX. */
    private static final String PROP_SUFFIX = ".properties";
    
    /** The Constant USER_DIR. */
    private static final String USER_DIR = System.getProperty("user.home");
    
    /** The Constant FILE_SEP. */
    public static final String FILE_SEP = System.getProperty("file.separator");
    
    /** The logger. */
    private static Logger logger = Logger.getLogger(Configuration.class.getName());
    

	/**
	 * Instantiates a new configuration.
	 * 
	 * @param propFileName the prop file name
	 * @param basePath the base path
	 * @param pkg the pkg
	 */
	public Configuration(String propFileName, String basePath, String pkg) {
		this.propFileName = propFileName;
		this.basePath = basePath;
		this.pkg = pkg;
		props = new Properties();
		if (!initProps()) {
			throw new IllegalStateException("Unable to load properties for "
					+ propFileName);
		}
	}


	/**
	 * Instantiates a new configuration.
	 * 
	 * @param propFileName the prop file name
	 * @param appConfDir the app conf dir
	 */
	public Configuration(String propFileName,String appConfDir) {
		this(propFileName, USER_DIR+FILE_SEP+appConfDir, DEFAULT_PROPS_PKG);
	}
	
	/**
	 * Instantiates a new configuration.
	 * 
	 * @param propFileName the prop file name
	 */
	public Configuration(String propFileName) {
		this(propFileName, USER_DIR, DEFAULT_PROPS_PKG);
	}
    
    /**
     * Inits the props.
     * 
     * @return true, if successful
     */
    private boolean initProps()
    {
        boolean success = true;
        // if props cant be found locally check the classpath
        if (!loadLocalProps())
        {
            String propsName;
            if (pkg.equals(""))
            { // this wont be needed when we move the props
                propsName = propFileName + PROP_SUFFIX;
            }
            else
            {
                propsName = pkg + "/" + propFileName + PROP_SUFFIX;
            }
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try
            {
            	//System.out.println("problem loading props from "+propsName);
                logger.finest("Trying to load props from " + propsName);
                InputStream in = cl.getResourceAsStream(propsName);
                setInputStream(in);
                if (in != null)
                {
                    props.load(getInputStream());                  
                }
                else
                {
                    success = false;
                }
            }
            catch (IOException ioe)
            {
            	
                success = false;
            }
        }
        return success;
    }
    
    /**
     * Sets the input stream.
     * 
     * @param propsStream the new input stream
     */
    private void setInputStream(InputStream propsStream)
    {
        buffStream = new BufferedInputStream(propsStream);
        try
        {
            buffStream.mark(buffStream.available() + 1);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Gets the input stream.
     * 
     * @return the input stream
     */
    public InputStream getInputStream()
    {
        try
        {
            buffStream.reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffStream;
    }
    
    /**
     * Load local props.
     * 
     * @return true, if successful
     */
    private boolean loadLocalProps(){
       	boolean success = true;
        String filename =  basePath + FILE_SEP +propFileName + PROP_SUFFIX;      
        logger.finest("Trying to load props from " + filename);
        try{
        	FileInputStream fis = new FileInputStream(filename);
        	setInputStream(fis);
        	props.load(fis);
        }catch(IOException ioe){
        	success = false;       	
        }  
        return success;
    }
    
    /**
     * Sets the property prefix.
     * 
     * @param propPrefix the new property prefix
     */
    public void setPropertyPrefix(String propPrefix) {
	   this.propPrefix = propPrefix;
	}
    
    /**
     * Gets the prop stream.
     * 
     * @return the prop stream
     */
    private OutputStream getPropStream() {
    	  String filename =  basePath + FILE_SEP +propFileName + PROP_SUFFIX;    
    	  FileOutputStream fos = null;
         
          File confDir = new File(basePath);
          if(!confDir.exists()){
        	  confDir.mkdir();
          }
          try{
        	logger.finest("Trying to save props to " + filename);
          	fos = new FileOutputStream(filename);
          }catch(IOException ioe){
             logger.severe("Unable to save property file:"+filename);	
          }  
          return fos;
	}
    
    /**
     * Prefixed key.
     * 
     * @param key the key
     * 
     * @return the string
     */
    private String prefixedKey(String key){
    	String prefixed = null;
    	if(propPrefix != null && !propPrefix.equals("")){ 
    		prefixed  = propPrefix+"."+key;
    	}else{
    		prefixed = key;
    	}
    	return prefixed;
    }
    //delegation methods (favor composition over inheritance!!)
    /**
     * Gets the property.
     * 
     * @param key the key
     * 
     * @return the property or null if not found
     */
    public String getProperty(String key){ 	
    	return props.getProperty(prefixedKey(key));
    }
    
    /**
     * Gets the property.
     * 
     * @param key the key
     * @param def the def
     * 
     * @return the property
     */
    public String getProperty(String key,String def){
    	return props.getProperty(prefixedKey(key),def);
    }
    
    /**
     * List.
     * 
     * @param ps the ps
     */
    public void list(PrintStream ps){
    	props.list(ps);
    }
    
    /**
     * List.
     * 
     * @param pw the pw
     */
    public void list(PrintWriter pw){
    	props.list(pw);
    }
    
    /**
     * Property names.
     * 
     * @return the enumeration
     */
    public Enumeration propertyNames(){
    	return props.propertyNames();
    }
    
    /**
     * Sets the property.
     * 
     * @param key the key
     * @param val the val
     * 
     * @return the object
     */
    public Object setProperty(String key,String val){
    	return props.setProperty(prefixedKey(key),val);
    }

	/**
	 * Contains.
	 * 
	 * @param value the value
	 * 
	 * @return true, if successful
	 */
	public boolean contains(Object value) {
		return props.contains(value);
	}

	/**
	 * Contains key.
	 * 
	 * @param key the key
	 * 
	 * @return true, if successful
	 */
	public boolean containsKey(Object key) {
		return props.containsKey(key);
	}

	/**
	 * Contains value.
	 * 
	 * @param value the value
	 * 
	 * @return true, if successful
	 */
	public boolean containsValue(Object value) {
		return props.containsValue(value);
	}

	/**
	 * Elements.
	 * 
	 * @return the enumeration
	 */
	public Enumeration elements() {
		return props.elements();
	}

	/**
	 * Entry set.
	 * 
	 * @return the sets the
	 */
	public Set entrySet() {
		return props.entrySet();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return props.equals(o);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return props.hashCode();
	}

	/**
	 * Checks if is empty.
	 * 
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return props.isEmpty();
	}

	/**
	 * Keys.
	 * 
	 * @return the enumeration
	 */
	public Enumeration keys() {
		return props.keys();
	}

	/**
	 * Key set.
	 * 
	 * @return the sets the
	 */
	public Set keySet() {
		return props.keySet();
	}

	/**
	 * Load.
	 * 
	 * @param inStream the in stream
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void load(InputStream inStream) throws IOException {
		props.load(inStream);
	}

	/**
	 * Put all.
	 * 
	 * @param t the t
	 */
	public void putAll(Map t) {
		props.putAll(t);
	}

	/**
	 * Removes the.
	 * 
	 * @param key the key
	 * 
	 * @return the object
	 */
	public Object remove(Object key) {
		return props.remove(key);
	}

	/**
	 * Size.
	 * 
	 * @return the int
	 */
	public int size() {
		return props.size();
	}

	/**
	 * Store.
	 * 
	 * @param out the out
	 * @param header the header
	 */
	public void store(OutputStream out, String header){
		try{
		props.store(out, header);
		out.flush();
		out.close();
		}catch(IOException ioe){
			logger.severe("Unable to save property file!");
		}
	}
	
	/**
	 * Store.
	 * 
	 * @param out the out
	 */
	public void store(OutputStream out) {
		try {
			props.store(out, "");
		} catch (IOException e) {
			logger.severe("Unable to save property file!");
		}
	}
	
	/**
	 * Store.
	 */
	public void store() {
		
		try {
			props.store(getPropStream(), "");
		} catch (IOException e) {
			logger.severe("Unable to save property file!");
		}
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return props.toString();
	}

	/**
	 * Values.
	 * 
	 * @return the collection
	 */
	public Collection values() {
		return props.values();
	}

	
}
