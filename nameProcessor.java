import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class nameProcessor{
	public static void main(String[] args) throws IOException{
		String gen= "";
		String let = "";
		String yr = "";
		boolean validInputs = true;
		String er ="";
		if (args.length == 0){
			Scanner in = new Scanner(System.in);
			System.out.println("Enter a year between 1880 and 2016 (inclusive).");
			int i = in.nextInt();
			if (i>=1880 && i<=2016){
				yr = Integer.toString(i);
			} else{
				er+="year ";
				validInputs = false;
			}
			System.out.println("Enter a letter.");
			let = in.next();
			if (!Character.isLetter(let.charAt(0))){
				er+= "letter ";
				validInputs = false;
			}
			System.out.println("Filter by gender? Enter y/n.");
			String ans = in.next();
			if (ans.equalsIgnoreCase("y")){
				System.out.println("Enter M/F.");
				ans = in.next();
				if (ans.equalsIgnoreCase("m")){
					gen = "Boys ";
				}else if (ans.equalsIgnoreCase("f")){
					gen = "Girls ";
				} else{
					er+= "gender ";
					validInputs = false;
				}

			}
		}
		if (args.length <3 && args.length>0){
			er+= " number of arguments.";
			validInputs = false;
		} 
		if (args.length >=3){
			if (args [2].equalsIgnoreCase("M")){
				gen="Boys ";
			}else if (args[2].equalsIgnoreCase("F")){
				gen="Girls ";
			}else{
				er+= "gender ";
				validInputs = false;
			}
			if (Integer.parseInt(args[0])>=1880 && 
			Integer.parseInt(args[0])<=2016){
				yr = args[0];
			}else{
				er+="year ";
				validInputs = false;
			}
			
			let = args[1];
			if (!Character.isLetter(let.charAt(0))){
				er+= "letter ";
				validInputs = false;
			}
		}
		
		if(validInputs){
			Stream <String> names = Files.lines(Paths.get("names/yob"+yr+".txt"));
			final String lt = let.toUpperCase();
			if (gen.equals("")){
				System.out.println("All names from " + yr+" starting with " + let);
			}else{
				System.out.println("\""+gen+"\" "+ "names from " + yr+" starting with " + let);
			}
			Iterator<String> iter;
			
			if (gen.equals("Girls ")){
				iter = names.filter(name->name.startsWith(lt))
					.filter(pop->pop.split(",")[1].equals ("F"))
						.map(nm->nm.split(",")[0]).iterator();
			} else if (gen.equals("Boys ")){
				iter = names.filter(name->name.startsWith(lt))
					.filter(pop->pop.split(",")[1].equals ("M"))
						.map(nm->nm.split(",")[0]).iterator();
			}else{
				iter = names.filter(name ->name.startsWith(lt))
					.map(nm->nm.split(",")[0]).iterator();
			}

		
			while(iter.hasNext()){
				System.out.println(iter.next());
			}
				
		} else{
			System.out.println("Invalid Inputs. Try again.");
			System.out.println("Error in "+er +"field(s)");
			System.out.println("-------------");
			System.out.println("Valid input is one of the following forms:");
			System.out.println("java nameProcessor [year] [letter] [M/F (optional)]");
			System.out.println("java nameProcessor");
			System.out.println("-------------");
		}		
	}
}

