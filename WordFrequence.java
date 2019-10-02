import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WordFrequence {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String pathname=args[1];
	//	System.out.println(pathname);
		BufferedReader read = new BufferedReader(new FileReader(pathname));
        String s,file="";
        while((s=read.readLine()) != null){
            file=file+s;
        }
        read.close();
   //     System.out.println(file);
        
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
//		for(i=0;i<26;i++) {
//			System.out.println(((char)(65+i))+"字母出现的频率："+String.format("%.2f",p[i]));
//		}
		
		Map<Character, Double> map=new HashMap<Character, Double>();
		for(i=0;i<26;i++) {
			map.put((char)(65+i), p[i]*100);
		}
		//按值排序
		ArrayList<Map.Entry<Character, Double>> list = new ArrayList<Map.Entry<Character, Double>>(map.entrySet());
		list.sort(new Comparator<Map.Entry<Character, Double>>() {
			public int compare(Map.Entry<Character, Double> o1,Map.Entry<Character, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		System.out.println("各个字母出现的频率如下：");
		for(i=0;i<list.size();i++) {
			System.out.println(list.get(i).getKey()+":"+String.format("%.2f",list.get(i).getValue())+"%");
		}
	}
}
