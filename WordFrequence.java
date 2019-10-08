import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WordFrequence {
	
	/*  第0步   */
	public static void stepzero(StringBuffer file) {
		//数组count统计26个字母 每个字母出现的次数
      	int count[]=new int[26];
      //数组p统计每个字母出现的频率
      	double p[]=new double[26];
      //sum统计文本中字母的总个数
      	int sum=0,a,i;
        for(i=0;i<file.length();i++){
        	char ch = file.charAt(i);
        	if(ch>=97&&ch<=122) {
				a=ch-97;
				count[a]++;
				sum++;
			}
			if(ch>=65&&ch<=90) {
				a=ch-65;
				count[a]++;
				sum++;
			}
        }
		for(i=0;i<26;i++) {
			p[i]=1.0*count[i]/sum;
		}
		System.out.println("字母的总个数为："+sum);
		Map<Character, Double> map=new HashMap<Character, Double>();
		for(i=0;i<26;i++) {
			map.put((char)(65+i), p[i]*100);
		}
		//按值排序
		ArrayList<Map.Entry<Character, Double>> list = new ArrayList<Map.Entry<Character, Double>>(map.entrySet());
		list.sort(new Comparator<Map.Entry<Character, Double>>() {
			public int compare(Map.Entry<Character, Double> o1,Map.Entry<Character, Double> o2) {
				double i=o1.getValue()-o2.getValue();
				if(i==0) {
					return o1.getKey().compareTo(o2.getKey());
				}
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		System.out.println("各个字母出现的频率如下：");
		for(i=0;i<list.size();i++) {
			System.out.println(list.get(i).getKey()+":"+String.format("%.2f",list.get(i).getValue())+"%");
		}
	}
	
	/*  按值排序   */
	public static ArrayList<Map.Entry<String, Integer>> sortbyValue(Map<String, Integer> map) {
		ArrayList<Map.Entry<String, Integer>> list=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		list.sort(new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {
				int i=o1.getValue()-o2.getValue();
				if(i==0) {
					return o1.getKey().compareTo(o2.getKey());
				}
				return o2.getValue().compareTo(o1.getValue());
			}
		});	
		return list;
	}
	
	/*  第一步      */
	public static void stepone1(StringBuffer file,int n) {
		//将所有大写字母转换为小写
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<file.length();i++) {
			char c=file.charAt(i);
			if(Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			}
			else {
				sb.append(c);
			}
		}
		String str = sb.toString().toLowerCase(); 
		String[] words=str.split("[^(a-z0-9)]+");
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(int i=0;i<words.length;i++) {
			//判断是否是以字母开头
			if(words[i].matches("[a-z]*")) {
				if(map.get(words[i])==null) {
					map.put(words[i], 1);
				}
				else {
					map.put(words[i], map.get(words[i])+1);
				}
			}
		}
		/*  功能1  */
		ArrayList<Map.Entry<String, Integer>> list=sortbyValue(map);
			if(n==-1) 
				n=list.size();
			System.out.println("前"+n+"个单词出现的次数如下：");
			for(int i=0;i<n;i++) {
				System.out.println(list.get(i).getKey()+":"+list.get(i).getValue());
		}
	}

	/* 第一步功能2:-d  */
	public static void stepone21(String pathname,int n) throws IOException {
		File myfile=new File(pathname);
		String[] list=myfile.list();
		for(String itemName : list){
			StringBuffer itemFile=readFile(pathname+itemName);
			System.out.println("---------------------------------------------------------");
			System.out.println("---------------------------------------------------------");
	        System.out.println(itemName+"文件中：");
	        stepone1(itemFile,n);
		}
	}
	
	/* 第一步功能2:-d -s  */
	public static void stepone22(String pathname,int n) throws IOException {
		File file=new File(pathname);
		File[] fileLFiles=file.listFiles();
		for(int i=0;i<fileLFiles.length;i++) {
			if(fileLFiles[i].isFile()) {
				String fileName=fileLFiles[i].getName();
				StringBuffer files=readFile(pathname+fileName);
				System.out.println(fileName+"文件中：");
				stepone1(files,n);
			}
			if(fileLFiles[i].isDirectory()) {
				String directoryName=fileLFiles[i].getName();
				String path=pathname+directoryName+"\\";
				System.out.println(directoryName+"目录中：");
				stepone22(path,n);
			}
		}
	}
	
	/*  第二步   */
	public static void steptwo(String pathOne,String pathTwo) throws IOException {
		StringBuffer fileOne=readFile(pathOne);
		StringBuffer fileTwo=readFile(pathTwo);
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		for(int i=0;i<fileOne.length();i++) {
			char c=fileOne.charAt(i);
			if(Character.isUpperCase(c)) {
				sb1.append(Character.toLowerCase(c));
			}
			else {
				sb1.append(c);
			}
		}
		String str1 = sb1.toString().toLowerCase(); 
		String[] wordsOne=str1.split("[^(a-z0-9)]+");
		for(int i=0;i<fileTwo.length();i++) {
			char c=fileTwo.charAt(i);
			if(Character.isUpperCase(c)) {
				sb2.append(Character.toLowerCase(c));
			}
			else {
				sb2.append(c);
			}
		}
		String str2 = sb2.toString().toLowerCase(); 
		String[] wordsTwo=str2.split("[^(a-z0-9)]+");
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(int i=0;i<wordsTwo.length;i++) {
			//System.out.println(wordsTwo[i]);
			//判断是否是以字母开头
			if(wordsTwo[i].matches("[a-z]*")) {
				//跳过停词表
				int flag=0; //0表示该单词不在停词表中 1表示在
				for(int j=0;j<wordsOne.length;j++) {
					if(wordsOne[j].equals(wordsTwo[i])) {
						//System.out.println(wordsOne[j]);
						flag=1;
						break;
					}
				}
				if(flag==0) {
					if(map.get(wordsTwo[i])==null) {
						map.put(wordsTwo[i], 1);
					}
					else 
						map.put(wordsTwo[i], map.get(wordsTwo[i])+1);
				}
			}
		}
		ArrayList<Map.Entry<String, Integer>> list=sortbyValue(map);
		System.out.println("跳过停词表后各个单词出现的次数如下：");
		for(int i=0;i<list.size();i++) 
			System.out.println(list.get(i).getKey()+":"+list.get(i).getValue());
	}
	
	/*  读文件    */
	public static StringBuffer readFile(String pathname) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(pathname));
        String s;
        StringBuffer file=new StringBuffer();
        while((s=read.readLine()) != null){
            file.append(s+'\n');
        }
        read.close();
        return file;
	}
	
	public static void main(String[] args) throws IOException {	
        if(args[0].compareTo("-c")==0) {
        	String pathname=args[1];
        	StringBuffer file=readFile(pathname);
        	stepzero(file);
        }
        else if(args[0].compareTo("-f")==0) {
        	if(args[1].compareTo("-n")==0) {
        		String pathname=args[3];
            	StringBuffer file=readFile(pathname);
        		int n = Integer.parseInt(args[2]);
        		stepone1(file,n);
        	}
        	else {
        		String pathname=args[1];
        		StringBuffer file=readFile(pathname);
        		stepone1(file,-1);
        	}
        }
        else if (args[0].compareTo("-d")==0) {
        	int n=-1;
        	if(args[1].compareTo("-s")!=0&&args[1].compareTo("-n")==0) {
        		String pathname=args[3];
        		n = Integer.parseInt(args[2]);
        		stepone21(pathname,n);
        	}
        	if(args[1].compareTo("-s")==0&&args[2].compareTo("-n")!=0){
        		String path=args[2];
        		stepone22(path,n);
        	}
        	if(args[1].compareTo("-s")==0&&args[2].compareTo("-n")==0) {
        		String path=args[4];
        		n = Integer.parseInt(args[3]);
        		stepone22(path, n);
        	}
        	if(args[1].compareTo("-s")!=0&&args[1].compareTo("-n")!=0){
        		String pathname=args[1];
        		stepone21(pathname,n);
        	}
        }
        else if(args[0].compareTo("-x")==0) {
        	String pathOne=args[1];
        	String pathTwo=args[3];
        	steptwo(pathOne, pathTwo);
        }
	}
}
