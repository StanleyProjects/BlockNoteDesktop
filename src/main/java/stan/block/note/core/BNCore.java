package stan.block.note.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import stan.block.note.core.notes.Note;
import stan.block.note.core.notes.cases.MultiLineText;
import stan.block.note.core.notes.cases.SingleLineText;
import stan.block.note.core.units.Block;
import stan.block.note.core.units.Table;

import stan.block.note.helpers.FileHelper;
import stan.block.note.helpers.json.JSONWriter;
import stan.block.note.helpers.json.JSONParser;

public class BNCore
{
    static private final String extension = "star";
    static private final String mainFile = "main";

    static private BNCore instance;
    static public BNCore getInstance()
    {
        if(instance == null)
        {
            instance = new BNCore();
        }
        return instance;
    }

    private String path;
    private HashMap main;
    //private List<Integer> idTree;
    private List<String> idTree;

    private BNCore()
    {
    }

    public boolean isHead()
    {
		return idTree.size() == 0;
	}
    public void backActualBlock()
    {
		if(isHead())
		{
			return;
		}
		idTree.remove(idTree.size()-1);
	}
    public void setActualBlock(String id)
    {
        HashMap block = getActualHashMap();
		ArrayList blocks = (ArrayList)block.get("blocks");
		if(blocks == null || blocks.size() <= 0)
		{
			return;
		}
		HashMap newBlock = getBlock(blocks, id);
		if(newBlock != null)
		{
			idTree.add(id);
		}
	}
    public Block getActualBlock()
    {
        Block block = null;
        HashMap map = getActualHashMap();
        if(map != null)
        {
            block = getBlockFromMap(map);
            block.blocks = new ArrayList<>();
            ArrayList blocks = (ArrayList)map.get("blocks");
            for(int i = 0; i < blocks.size(); i++)
            {
                block.blocks.add(getBlockFromMap((HashMap)blocks.get(i)));
            }
            block.tables = new ArrayList<>();
            ArrayList tables = (ArrayList)map.get("tables");
            for(int i = 0; i < tables.size(); i++)
            {
                block.tables.add(getTableFromMap((HashMap)tables.get(i)));
            }
        }
        return block;
    }
    public Block getBlockFromMap(HashMap map)
    {
        HashMap datesMap = (HashMap)map.get("dates");
        Dates dates = new Dates((Long)datesMap.get("create"));
        dates.update = (Long)datesMap.get("update");
        dates.sync = (Long)datesMap.get("sync");
        return new Block((String)map.get("id"), (String)map.get("name"), (String)map.get("color"), dates);
    }
    public Table getTableFromMap(HashMap map)
    {
        HashMap datesMap = (HashMap)map.get("dates");
        Dates dates = new Dates((Long)datesMap.get("create"));
        dates.update = (Long)datesMap.get("update");
        dates.sync = (Long)datesMap.get("sync");
        return new Table((String)map.get("id"), (String)map.get("name"), (String)map.get("color"), dates);
    }
    private HashMap getActualHashMap()
    {
        ArrayList blocks = null;
        HashMap block = main;
        for(int i = 0; i < idTree.size(); i++)
        {
            blocks = (ArrayList)block.get("blocks");
            //if(blocks != null && blocks.size() > 0 && blocks.size() <= idTree.get(i))
            if(blocks != null && blocks.size() > 0)
            {
                block = getBlock(blocks, idTree.get(i));
            }
            else
            {
                return null;
            }
        }
        return block;
    }
    public Table getTable(String id)
    {
        Table table = null;
        HashMap map = getTableHashMap(id);
        if(map != null)
        {
            table = getTableFromMap(map);
            table.notes = new ArrayList<>();
            ArrayList notes = (ArrayList)map.get("notes");
            for(int i = 0; i < notes.size(); i++)
            {
				table.notes.add(getNote((HashMap)notes.get(i)));
            }
        }
        return table;
    }
    private HashMap getTableHashMap(String id)
    {
        HashMap map = getActualHashMap();
		ArrayList tables = (ArrayList)map.get("tables");
		for(int i = 0; i < tables.size(); i++)
		{
			HashMap table = (HashMap)tables.get(i);
			String tableId = (String)table.get("id");
			if(tableId.equals(id))
			{
				return table;
			}
		}
        return null;
	}
	public Note getNote(HashMap map)
    {
		Note note = new Note();
		note.id = (String)map.get("id");
		note.color = (String)map.get("color");
		note.settings = (HashMap)map.get("settings");
		note.cases = new ArrayList<>();
        return note;
    }
    private HashMap getBlock(ArrayList blocks, String id)
    {
        for(int i = 0; i < blocks.size(); i++)
        {
            HashMap block = (HashMap)blocks.get(i);
            if(block != null && block.get("id").equals(id))
            {
                return block;
            }
        }
        return null;
    }

    private void updateDates()
    {
        updateDates(main, 0);
    }
    private void updateDates(HashMap block, int next)
    {
        if(next + 1 < idTree.size())
        {
            ArrayList blocks = (ArrayList)block.get("blocks");
			for(int i = 0; i < blocks.size(); i++)
			{
				HashMap tmp = (HashMap)blocks.get(i);
				String id = (String)tmp.get("id");
				if(id.equals(idTree.get(next)))
				{
					updateDates(tmp, next + 1);
					break;
				}
			}
        }
        updateDates(block);
    }
    private void updateDates(HashMap unit)
    {
        HashMap dates = (HashMap)unit.get("dates");
        dates.put("update", new Date().getTime());
    }

    private HashMap createEmptyBlock(String name)
    {
        HashMap map = createEmptyUnit(name);
        map.put("blocks", new Object[0]);
        map.put("tables", new Object[0]);
        return map;
    }
    private HashMap createEmptyTable(String name)
    {
        HashMap map = createEmptyUnit(name);
        map.put("notes", new Object[0]);
        return map;
    }
    private HashMap createEmptyUnit(String name)
    {
        HashMap map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString());
        map.put("name", name);
        map.put("color", Colors.RED);
        map.put("dates", createNewDates());
        return map;
	}
    private HashMap createEmptyNote()
    {
        HashMap map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString());
        map.put("color", Colors.RED);
        map.put("dates", createNewDates());
        map.put("settings", new HashMap<>());
        return map;
    }
    private HashMap createNewDates()
    {
        long create = new Date().getTime();
        HashMap dates = new HashMap<String, Object>();
        dates.put("create", create);
        dates.put("update", create);
        dates.put("sync", -1);
		return dates;
	}


    public void updateData()
    {
        ZipFile zf = null;
        try
        {
            zf = new ZipFile(path);
        }
        catch(Exception e)
        {
            System.out.println("ZipFile - " + e.getMessage());
        }
        ZipEntry entry = getZipEntryFromName(zf, "main");
        try
        {
            main = getMapFromString(FileHelper.readFile(zf.getInputStream(entry)));
        }
        catch(Exception e)
        {
            System.out.println("getMapFromString - " + e.getMessage());
        }
	}
    public void openBlockNote(String fullPath)
    {
        path = fullPath;
		updateData();
        idTree = new ArrayList<>();
    }
    private ZipEntry getZipEntryFromName(ZipFile zf, String name)
    {
        for(Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();)
        {
            ZipEntry entry = e.nextElement();
            if(entry.getName().equals(name))
            {
                return entry;
            }
        }
        return null;
    }
    private ZipEntry getZipEntryFromName(ZipInputStream zis, String name)
		throws IOException
    {
        ZipEntry entry = zis.getNextEntry();
        while(entry != null)
        {
            if(entry.getName().equals(name))
            {
                return entry;
            }
            entry = zis.getNextEntry();
        }
        return null;
    }
    private HashMap getMapFromString(String result)
    {
        JSONParser parser = new JSONParser();
        try
        {
            return (HashMap)parser.parse(result);
        }
        catch(Exception e)
        {
            System.out.println("JSONParser - " + e.getMessage());
        }
        return null;
    }
    public void createBlockNote(String path, String name)
    {
        String data = null;
        try
        {
            data = JSONWriter.mapToJSONString(createEmptyBlock(name));
        }
        catch(Exception e)
        {
            System.out.println("mapToJSONString - " + e.getMessage());
            return;
        }
        File main = new File(path + "/main");
        try
        {
            FileHelper.writeFile(data, path + "/main");
        }
        catch(Exception e)
        {
            System.out.println("writeFile - " + e.getMessage());
            return;
        }
        ZipOutputStream zos = null;
        try
        {
            zos = new ZipOutputStream(new FileOutputStream(path + "/" + name + "." + extension));
        }
        catch(Exception e)
        {
            System.out.println("ZipOutputStream - " + e.getMessage());
            return;
        }
        try
        {
            addFileToZip(main, zos);
        }
        catch(IOException e)
        {
            System.out.println("addFileToZip - " + e.getMessage());
            return;
        }
        main.delete();
        try
        {
            zos.close();
        }
        catch(IOException e)
        {
            System.out.println("zos.close - " + e.getMessage());
            return;
        }
    }

    private void editUnit(HashMap unit, String name, String color)
    {
        unit.put("name", name);
        unit.put("color", color);
        updateDates(unit);
        updateDates();
        updateBlockNote();
    }

    public void putNewNote(String tableId, String keySettings, HashMap mapSettings)
    {
        HashMap tableMap = getTableHashMap(tableId);
        if(tableMap == null)
        {
            return;
        }
		HashMap note = createEmptyNote();
		HashMap settings = (HashMap)note.get("settings");
		settings.put(keySettings, mapSettings);
		ArrayList notes = (ArrayList)tableMap.get("notes");
		clearEmptyNotes(notes);
        notes.add(note);
        updateDates();
        updateBlockNote();
    }
    private void clearEmptyNotes(ArrayList notes)
    {
		int i=0;
		while(i<notes.size())
		{
			HashMap noteMap = (HashMap)notes.get(i);
			if(noteMap.get("cases") == null)
			{
				notes.remove(i);
			}
			else
			{
				i++;
			}
		}
	}
    public void putNewTable()
    {
        HashMap map = getActualHashMap();
        if(map == null)
        {
            return;
        }
        ArrayList tables = (ArrayList)map.get("tables");
        tables.add(createEmptyTable("new Table"));
        updateDates();
        updateBlockNote();
    }
    public void editTable(String id, String name, String color)
    {
        HashMap map = getActualHashMap();
        if(map == null)
        {
            return;
        }
        ArrayList tables = (ArrayList)map.get("tables");
        for(int i=0; i<tables.size(); i++)
        {
            HashMap table = (HashMap)tables.get(i);
            String idTable = (String) table.get("id");
            if(idTable.equals(id))
            {
                editUnit(table, name, color);
                break;
            }
        }
    }
    public void deleteTable(String id)
    {
        HashMap map = getActualHashMap();
        if(map == null)
        {
            return;
        }
        ArrayList tables = (ArrayList)map.get("tables");
        for(int i=0; i<tables.size(); i++)
        {
            HashMap table = (HashMap)tables.get(i);
            String idTable = (String)table.get("id");
            if(idTable.equals(id))
            {
                tables.remove(i);
                break;
            }
        }
        updateDates();
        updateBlockNote();
    }
	
    public void updateBlockNote()
    {
        String data = null;
        try
        {
            data = JSONWriter.mapToJSONString(main);
        }
        catch(Exception e)
        {
            System.out.println("mapToJSONString - " + e.getMessage());
            return;
        }
        updateBlockNote("main", data);
    }
    public void putNewBlock()
    {
        HashMap map = getActualHashMap();
        if(map == null)
        {
            return;
        }
        ArrayList blocks = (ArrayList)map.get("blocks");
        blocks.add(createEmptyBlock("new Block"));
        updateDates();
        updateBlockNote();
    }
    public void editBlock(String id, String name, String color)
    {
        HashMap map = getActualHashMap();
        if(map.get("id").equals(id))
        {
            editUnit(map, name, color);
            return;
        }
        ArrayList blocks = (ArrayList)map.get("blocks");
        for(int i=0; i<blocks.size(); i++)
        {
            HashMap block = (HashMap)blocks.get(i);
            String idBlock = (String) block.get("id");
            if(idBlock.equals(id))
            {
                editUnit(block, name, color);
                break;
            }
        }
    }
    public void deleteBlock(String id)
    {
        HashMap map = getActualHashMap();
        if(map == null)
        {
            return;
        }
        ArrayList blocks = (ArrayList)map.get("blocks");
        for(int i=0; i<blocks.size(); i++)
        {
            HashMap block = (HashMap)blocks.get(i);
            String idBlock = (String)block.get("id");
            if(idBlock.equals(id))
            {
                blocks.remove(i);
                break;
            }
        }
        updateDates();
        updateBlockNote();
    }

    private void addFileToZip(String fileName, ZipOutputStream zos)
		throws IOException
    {
        File file = new File(fileName);
        addFileToZip(file, zos);
    }
    private void addFileToZip(File file, ZipOutputStream zos)
		throws IOException
    {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
        writeData(zos, fis);
        zos.closeEntry();
        fis.close();
    }
    private void writeData(ZipOutputStream zos, InputStream is)
		throws IOException
    {
        byte[] bytes = new byte[1024];
        int length;
        while((length = is.read(bytes)) >= 0)
        {
            zos.write(bytes, 0, length);
        }
    }
    private void updateBlockNote(String name, String data)
    {
        ZipOutputStream zos = null;
        try
        {
            zos = new ZipOutputStream(new FileOutputStream(path));
        }
        catch(Exception e)
        {
            System.out.println("ZipOutputStream - " + e.getMessage());
            return;
        }
        try
        {
            writeDataToZip(zos, name, data);
        }
        catch(Exception e)
        {
            System.out.println("writeDataToZip - " + e.getMessage());
        }
    }
    private void writeDataToZip(ZipOutputStream zos, String name, String data)
		throws IOException
    {
        zos.putNextEntry(new ZipEntry(name));
        InputStream is = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        writeData(zos, is);
        is.close();
        zos.closeEntry();
        zos.close();
    }
}