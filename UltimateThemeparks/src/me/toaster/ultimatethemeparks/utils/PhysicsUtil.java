package me.toaster.ultimatethemeparks.utils;

import org.bukkit.Location;

public class PhysicsUtil{
    /**
     * Rotates coordinate around another coordinate along an axis x
     * @param point what coordinate you want to return 
     * @param x
     * @param y
     * @param z
     * @param cx
     * @param cy
     * @param cz
     * @param theta angle 
     * @return
     */
	
    public static double rotateAroundX(String point, double x, double y, double z, double cx, double cy, double cz, double theta)
    {
    	
    	Matrix3 xrot = new Matrix3(1,0,0,0,Math.cos(Math.toRadians(theta)),-(Math.sin(Math.toRadians(theta))),0,Math.sin(Math.toRadians(theta)),Math.cos(Math.toRadians(theta)));
    	Matrix3 loc = new Matrix3(x-cx, 0, 0, 0, y-cy, 0, 0, 0, z-cz);
    	
    	Matrix3 multiplied = xrot.multiply(loc);
    	
    	double row1 =(multiplied.a11+multiplied.a12+multiplied.a13)+cx;
    	double row2 =(multiplied.a21+multiplied.a22+multiplied.a23)+cy;
    	double row3 =(multiplied.a31+multiplied.a32+multiplied.a33)+cz;
    	
    	if(point.equalsIgnoreCase("x"))
    	{
    		return row1;
    	}
    	
    	else if(point.equalsIgnoreCase("y"))
    	{
    		return row2;
    	}
    	
    	else if(point.equalsIgnoreCase("z"))
    	{
    		return row3;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    /**
     * Rotates coordinate around another coordinate along an axis y
     * @param point what coordinate you want to return
     * @param x
     * @param y
     * @param z
     * @param cx
     * @param cy
     * @param cz
     * @param theta angle
     * @return
     */
    
    public static double rotateAroundY(String point, double x, double y, double z, double cx, double cy, double cz, double theta)
    {

    	
    	Matrix3 xrot = new Matrix3(Math.cos(Math.toRadians(theta)), 0, Math.sin(Math.toRadians(theta)), 0, 1, 0, -(Math.sin(Math.toRadians(theta))), 0, Math.cos(Math.toRadians(theta)));
    	Matrix3 loc = new Matrix3(x-cx, 0, 0, 0, y-cy, 0, 0, 0, z-cz);
    	
    	Matrix3 multiplied = xrot.multiply(loc);
    	
    	double row1 =(multiplied.a11+multiplied.a12+multiplied.a13)+cx;
    	double row2 =(multiplied.a21+multiplied.a22+multiplied.a23)+cy;
    	double row3 =(multiplied.a31+multiplied.a32+multiplied.a33)+cz;
    	
    	if(point.equalsIgnoreCase("x"))
    	{
    		return row1;
    	}
    	
    	else if(point.equalsIgnoreCase("y"))
    	{
    		return row2;
    	}
    	
    	else if(point.equalsIgnoreCase("z"))
    	{
    		return row3;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    /**
     * Rotates coordinate around another coordinate along an axis y
     * @param point coordinate you want to return
     * @param x
     * @param y
     * @param z
     * @param cx
     * @param cy
     * @param cz
     * @param theta angle
     * @return
     */

    public static double rotateAroundZ(String point, double x, double y, double z, double cx, double cy, double cz, double theta)
    {

    	
    	Matrix3 xrot = new Matrix3(Math.cos(Math.toRadians(theta)), -(Math.sin(Math.toRadians(theta))), 0, Math.sin(Math.toRadians(theta)), Math.cos(Math.toRadians(theta)), 0, 0, 0, 1);
    	Matrix3 loc = new Matrix3(x-cx, 0, 0, 0, y-cy, 0, 0, 0, z-cz);

    	
    	Matrix3 multiplied = xrot.multiply(loc);
    		
    	double row1 =(multiplied.a11+multiplied.a12+multiplied.a13)+cx;
    	double row2 =(multiplied.a21+multiplied.a22+multiplied.a23)+cy;
    	double row3 =(multiplied.a31+multiplied.a32+multiplied.a33)+cz;
    			
    	
    	if(point.equalsIgnoreCase("x"))
    	{
    		return row1;
    	}
    	
    	else if(point.equalsIgnoreCase("y"))
    	{
    		return row2;
    	}
    	
    	else if(point.equalsIgnoreCase("z"))
    	{
    		return row3;
    	}
    	else
    	{
    		return 0;
    	}
    }
	

	///////////////
    
    
    /**
     * Rotates coordinate around another coordinate along an axis 
     * @param point
     * @param location
     * @param c location to rotate round
     * @param theta angle
     * @return
     */
	
    public static double rotateAroundX(String point, Location l, Location c, double theta)
    {
    	double x = l.getX();
    	double y = l.getY();
    	double z = l.getZ();
    	
    	double cx = c.getX();
    	double cy = c.getY();
    	double cz = c.getZ();
    	
    	
    	Matrix3 xrot = new Matrix3(1,0,0,0,Math.cos(Math.toRadians(theta)),-(Math.sin(Math.toRadians(theta))),0,Math.sin(Math.toRadians(theta)),Math.cos(Math.toRadians(theta)));
    	Matrix3 loc = new Matrix3(x-cx, 0, 0, 0, y-cy, 0, 0, 0, z-cz);
    	
    	Matrix3 multiplied = xrot.multiply(loc);
    	
    	double row1 =(multiplied.a11+multiplied.a12+multiplied.a13)+cx;
    	double row2 =(multiplied.a21+multiplied.a22+multiplied.a23)+cy;
    	double row3 =(multiplied.a31+multiplied.a32+multiplied.a33)+cz;
    	
    	if(point.equalsIgnoreCase("x"))
    	{
    		return row1;
    	}
    	
    	else if(point.equalsIgnoreCase("y"))
    	{
    		return row2;
    	}
    	
    	else if(point.equalsIgnoreCase("z"))
    	{
    		return row3;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    
    /**
     * Rotates coordinate around another coordinate along an axis y
     * @param point
     * @param location
     * @param c location to rotate round
     * @param theta angle
     * @return
     */
    
    public static double rotateAroundY(String point, Location l, Location c, double theta)
    {
    	double x = l.getX();
    	double y = l.getY();
    	double z = l.getZ();
    	
    	double cx = c.getX();
    	double cy = c.getY();
    	double cz = c.getZ();
    	
    	Matrix3 xrot = new Matrix3(Math.cos(Math.toRadians(theta)), 0, Math.sin(Math.toRadians(theta)), 0, 1, 0, -(Math.sin(Math.toRadians(theta))), 0, Math.cos(Math.toRadians(theta)));
    	Matrix3 loc = new Matrix3(x-cx, 0, 0, 0, y-cy, 0, 0, 0, z-cz);
    	
    	Matrix3 multiplied = xrot.multiply(loc);
    	
    	double row1 =(multiplied.a11+multiplied.a12+multiplied.a13)+cx;
    	double row2 =(multiplied.a21+multiplied.a22+multiplied.a23)+cy;
    	double row3 =(multiplied.a31+multiplied.a32+multiplied.a33)+cz;
    	
    	if(point.equalsIgnoreCase("x"))
    	{
    		return row1;
    	}
    	
    	else if(point.equalsIgnoreCase("y"))
    	{
    		return row2;
    	}
    	
    	else if(point.equalsIgnoreCase("z"))
    	{
    		return row3;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    
    /**
     * 
     * @param point
     * @param location
     * @param c location to rotate round
     * @param theta angle
     * @return
     */

    public static double rotateAroundZ(String point, Location l, Location c, double theta)
    {
    	double x = l.getX();
    	double y = l.getY();
    	double z = l.getZ();
    	
    	double cx = c.getX();
    	double cy = c.getY();
    	double cz = c.getZ();
    	
    	Matrix3 xrot = new Matrix3(Math.cos(Math.toRadians(theta)), -(Math.sin(Math.toRadians(theta))), 0, Math.sin(Math.toRadians(theta)), Math.cos(Math.toRadians(theta)), 0, 0, 0, 1);
    	Matrix3 loc = new Matrix3(x-cx, 0, 0, 0, y-cy, 0, 0, 0, z-cz);

    	
    	Matrix3 multiplied = xrot.multiply(loc);
    		
    	double row1 =(multiplied.a11+multiplied.a12+multiplied.a13)+cx;
    	double row2 =(multiplied.a21+multiplied.a22+multiplied.a23)+cy;
    	double row3 =(multiplied.a31+multiplied.a32+multiplied.a33)+cz;
    			
    	
    	if(point.equalsIgnoreCase("x"))
    	{
    		return row1;
    	}
    	
    	else if(point.equalsIgnoreCase("y"))
    	{
    		return row2;
    	}
    	
    	else if(point.equalsIgnoreCase("z"))
    	{
    		return row3;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    public static Location rotateAroundX(Location l, Location center, double theta) {
    	if(theta == 0) {
    		return l;
    	}
    	double x = rotateAroundX("x", l, center, theta);
    	double y = rotateAroundX("y", l, center, theta);
    	double z = rotateAroundX("z", l, center, theta);
    	return new Location(l.getWorld(), x, y, z,l.getYaw(),0);
    }
    
    public static Location rotateAroundY(Location l, Location center, double theta) {
    	if(theta == 0) {
    		return l;
    	}
    	double x = rotateAroundY("x", l, center, theta);
    	double y = rotateAroundY("y", l, center, theta);
    	double z = rotateAroundY("z", l, center, theta);
    	return new Location(l.getWorld(), x, y, z, l.getYaw()-(float)theta,0);
    }
    
    public static Location rotateAroundZ(Location l, Location center, double theta) {
    	if(theta == 0) {
    		return l;
    	}
    	double x = rotateAroundZ("x", l, center, theta);
    	double y = rotateAroundZ("y", l, center, theta);
    	double z = rotateAroundZ("z", l, center, theta);
    	return new Location(l.getWorld(), x, y, z, l.getYaw(),0);
    }
    
}