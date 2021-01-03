package de.basicbit.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
	
	public static ArrayList<Class<?>> findClasses(String pckgname) throws ClassNotFoundException {
	    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	    
		try {
			JarFile jar = new JarFile(new File("./plugins/ServerSystem.jar"));
			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				
				if (name.startsWith(pckgname.replace(".", "/") + "/") && name.endsWith(".class")) {
					String className = name.replace("/", ".");
					className = className.substring(0, className.length() - 6);
					classes.add(Class.forName(className));
				}
			}
			
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return classes;
	}
}
