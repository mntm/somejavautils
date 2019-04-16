package com.github.mntm.somejavautils;


import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Helper class to do various RMI tasks.
 */
public class RMIUtils {
    public static void initSecurityManager() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    /**
     * @param hostname IP address where to find rmiregistry (usually the local ip address)
     * @param port     Port on which rmiregistry is listening
     * @param name     The name of the shared object to retrieve
     * @return A stub to the requested object
     */
    public static Remote getStub(String hostname, int port, String name) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(hostname, port);
        return registry.lookup(name);
    }

    /**
     * Register a Remote object on the registry
     * <p>
     * If you want to export the object on a specific port, define
     * `rmi.object.export-port` property in your launching environement
     * <p>
     * e.g: `java -jar -Drmi.object.export-port="5029" myjarfile`
     *
     * @param hostname IP address where to find rmiregistry (usually the local ip address)
     * @param port     Port on which rmiregistry is listening
     * @param server   The remote object to be exported
     * @param name     The name to be associated to this server
     */
    public static void register(String hostname, int port, Remote server, String name) throws RemoteException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        int xPort = 0;

        try {
            xPort = Integer.parseInt(System.getProperty("rmi.object.export-port"));
        } catch (Exception ignored) {
        }

        Remote stub = UnicastRemoteObject.exportObject(server, xPort);

        Registry registry = LocateRegistry.getRegistry(hostname, port);

        registry.rebind(name, stub);
    }
}
