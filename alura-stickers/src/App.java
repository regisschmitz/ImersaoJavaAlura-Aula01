import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    static String ApiKey = "";
    static String UrlMain = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
    static String star = "\u2605";

    public static void main(String[] args) throws Exception {
        var Url = String.format(UrlMain, ApiKey);

        // Fazer a conexao
        var endereco = URI.create(Url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();    
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        var body = response.body();
    
        // Extrair dados que interessam (titulo, poster, classificacao)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            double notaInteira = Double.parseDouble(filme.get("imDbRating"));
            
            // Para gerar as estrelas do Rating
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < notaInteira; i++){
                stars.append(star);
            }
            System.out.println(filme.get("rank") + " - " + filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println("\u001B[33m" + stars.toString() + "\u001B[0m " + notaInteira);
            System.out.println(); 
        }
    }
}
