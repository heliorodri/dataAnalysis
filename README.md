# Analisador de arquivos ".dat"

## Antes de utilizarmos é necessário iniciar a aplicação

### Para isso você deve, através do terminal, navegar até o diretório do projeto e executar o seguinte comando:

    sudo bash startup.sh

#### Feito isso você poderá utilizar o leitor de arquivos, basta inserir o arquivo com a extensão '.dat' na pasta *./dataAnalysis/data/in* e visualizar a saída em *./dataAnalysis/data/out*
#
### Para que a leitura ocorra sem problemas e necessário que o arquivo esteja formatado com um registro em cada linha, separando as colunas pelo caracter 'ç' e sem espacos entre as vendas no array. Como no exemplo:
    
    001ç1234567891234çPessoaç50000
    002ç2345675434544345çOutra pessoaçRural
    003ç01ç[1-10-100,2-30-2.50,3-40-3.10]çPessoa


