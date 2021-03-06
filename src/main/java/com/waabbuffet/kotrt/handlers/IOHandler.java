package com.waabbuffet.kotrt.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class IOHandler {

	public static boolean injectNBTToFile(NBTTagCompound cmp, File f) {
		try {
			CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static NBTTagCompound getTagCompoundInFile(File f) {
		try {
			NBTTagCompound cmp = CompressedStreamTools.readCompressed(new FileInputStream(f));
			return cmp;
		} catch (IOException e) {
			NBTTagCompound cmp = new NBTTagCompound();
			try {
				CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
				return getTagCompoundInFile(f);
			} catch (IOException e1) {
				return null;
			}
		}
	}
	
	public static File createOrGetNBTFile(File f) {
		try {
			CompressedStreamTools.readCompressed(new FileInputStream(f));
		} catch (Exception e) {
			NBTTagCompound cmp = new NBTTagCompound();
			try {
				CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return f;
	}
	
}