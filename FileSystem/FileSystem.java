import java.io.*;
import java.util.*;

public class FileSystem {
	static Folder root;
	public static void main(String[] args) throws Exception {
		//Scanner sc = new Scanner(new File("d.in"));
		Scanner sc = new Scanner(System.in);
		for(int caseCt = 1 ;; caseCt++){
			int c = sc.nextInt(); //commands
			int queries = sc.nextInt(); //queries
			if(c == 0 && queries == 0) break;
			root = new Folder();
			while(c --> 0){
				String cmd = sc.next();
				if(cmd.equals("echo")){
					String[] line = sc.nextLine().split("\"");
					String content = line[1];
					Queue<String> dir = new LinkedList<>();
					String name = format(line[2].trim(), dir);
					Folder loc = root.getFolder(dir);
					loc.getFile(name).content = content;
				}else if(cmd.equals("cp")){
					Queue<String> srcDir = new LinkedList<>(); //dir of source
					String srcName = format(sc.next(), srcDir); //name of source
					Queue<String> destDir = new LinkedList<>(); //dir of dest
					String destName = format(sc.next(), destDir); //name of dest
					File src = root.getFolder(srcDir).getFile(srcName);
					File dest = root.getFolder(destDir).getFile(destName);
					dest.content = src.content;
				}else if(cmd.equals("mv")){
					Queue<String> srcDir = new LinkedList<>(); //dir of source
					String srcName = format(sc.next(), srcDir); //name of source
					Queue<String> destDir = new LinkedList<>(); //dir of dest
					String destName = format(sc.next(), destDir); //name of dest
					File move = root.getFolder(srcDir).files.remove(srcName); //remove the file
					File copy = root.getFolder(destDir).getFile(destName);
					copy.content = move.content; //copy contents
				}else if(cmd.equals("rm")){
					Queue<String> srcDir = new LinkedList<>(); //dir of source
					String srcName = format(sc.next(), srcDir); //name of source
					root.getFolder(srcDir).files.remove(srcName);
				}else if(cmd.equals("mkdir")){
					Queue<String> srcDir = new LinkedList<>(); //dir of source
					String srcName = format(sc.next(), srcDir); //name of source
					root.getFolder(srcDir).folders.put(srcName, new Folder());
				}else if(cmd.equals("rmdir")){
					Queue<String> srcDir = new LinkedList<>(); //dir of source
					String srcName = format(sc.next(), srcDir); //name of source
					root.getFolder(srcDir).folders.remove(srcName);
				}
			}
			System.out.printf("Case %d:\n", caseCt);
			for(int q = 1; q <= queries; q++){
				Queue<String> srcDir = new LinkedList<>(); //dir of source
				String srcName = format(sc.next(), srcDir); //name of source
				Folder src = root.getFolder(srcDir);
				String out = src == null ? "invalid": src.getFile(srcName).content;
				System.out.printf("Query %d: %s\n", q, out);
			}
		}
	}
	
	//Returns the name of a file and places parent folders in queue
	static String format(String line, Queue<String> out){
		String[] list = line.split("/");
		for(int i = 0; i < list.length-1; i++) out.add(list[i]);
		return list[list.length-1];
	}
	
	//Track the contents of each folder
	static class Folder{
		Map<String, Folder> folders = new HashMap<>();
		Map<String, File> files = new HashMap<>();
		
		Folder getFolder(Queue<String> q){
			if(q.isEmpty()) return this; //no more folders
			Folder f = folders.get(q.poll());
			if(f == null) return null; //invalid folder
			else return f.getFolder(q);
		}
		
		File getFile(String name){
			if(!files.containsKey(name)) files.put(name, new File());
			File f = files.get(name);
			return f;
		}
	}
	
	static class File{
		String content = "invalid";
	}
}
