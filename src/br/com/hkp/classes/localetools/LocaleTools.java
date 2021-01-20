/*
arquivo LocaleTools.java criado a partir de 28 de julho de 2018
*/
package br.com.hkp.classes.localetools;

import java.util.Locale;
import java.text.NumberFormat;


/**
 * A classe fornece metodos utilitarios para Locales e definicoes de constantes.
 * 
 * @author Hugo Kaulino Pereira
 * @version 1.0
 * @since 1.0
 */
public class LocaleTools
{
    /**
     * Locale para Portugues do Brasil
     */
    public static final Locale PT_BR = new Locale("pt","BR");
    /**
     * Locale para Ingles dos EUA
     */
    public static final Locale EN_US = new Locale("en","US");
    /**
     * Locale para Frances da Franca
     */
    public static final Locale FR_FR = new Locale("fr","FR");
    /**
     * Locale para Japones do Japao
     */
    public static final Locale JA_JP = new Locale("ja","JP");
    
    
    /**
     * Metodo que retorna o caractere usado para representar ponto decimal de 
     * acordo com o Locale passado como argumento. Esse caractere eh o utilizado
     * por printf e o metodo format da classe Formatter para representar ponto
     * decimal para este Locale especifico.
     * 
     * @param locale O Locale. Note-se que nem todo Locale eh reconhecido pelos 
     * metodos de formatacao de String da API java. Geralmente, se nao ha uma 
     * representacao especifica para o Locale passado como argumento, entao o 
     * ponto eh utilizado para representar ponto decimal.
     * @return O caractere usado para representar ponto decimal para este Locale
     * 
     * @since 1.0
     */
    /*[01]----------------------------------------------------------------------
    *    Retorna o caractere usado para ponto decimal para o Locale passado
    *    como argumento.
    --------------------------------------------------------------------------*/
    public static char decimalPoint(Locale locale)
    {
       return String.format(locale,"%.1f",0.1).charAt(1);
    }//fim de decimalPoint()
    
    /**
     * Metodo que retorna o caractere usado como separador de milhares de 
     * acordo com o Locale passado como argumento. Esse caractere eh o utilizado
     * por printf e o metodo format da classe Formatter para representar o
     * separador de milhares de acordo com o locale especifico.
     * 
     * @param locale O Locale. Note-se que nem todo Locale eh reconhecido pelos 
     * metodos de formatacao de String da API java. Geralmente, se nao ha uma 
     * representacao especifica para o Locale passado como argumento, entao a
     * virgula eh utilizada para representar o separador.
     * 
     * @return O caractere usado para representar o separador.
     * 
     * @since 1.0
     */
    /*[02]----------------------------------------------------------------------
    *   O caractere usado para separador de agrupamento para o Locale passado
    *   como argumento.
    --------------------------------------------------------------------------*/
    public static char separator(Locale locale)
    {
       return String.format(locale,"%,f",1234.0).charAt(1);
    }//fim de separator()
    
    /**
     * Os metodos {@link #decimalPoint(java.util.Locale) } e 
     * {@link #separator(java.util.Locale) } desta classe tentam identificar
     * qual caractere um determinado Locale utiliza para representar o ponto 
     * decimal e o separador de agrupamentos, respectivamente, em formatos 
     * numericos.
     * <p>
     * Esta identificacao pode falhar caso o numero nao seja representado de 
     * forma padrao, com digitos de 0 a 9 e separador de milhares. Para alguns
     * Locales os algarismos sao representados por outros caracteres e a 
     * representacao numerica pode tambem nao utilizar separadores de 
     * agrupamento.
     * <p>
     * Este metodo testa se a representacao numerica para um determinado Locale
     * permite identificar corretamente quais sao os caracteres para digitos,
     * separador e ponto decimal.
     * <p>
     * Se este metodo retornar false para algum Locale eh recomendavel que este
     * nao seja utilizado, pois varios metodos podem falhar ao tentar processar
     * Strings representando valores numericos, caso estes tenham sido obtidos
     * de acordo com os padroes deste Locale especifico.
     * 
     * @param locale O Locale a ser testado.
     * 
     * @return true se o teste foi OK. false se nao.
     */
    /*[03]----------------------------------------------------------------------
    *    Testa se o locale passado como argumento representa digitos com os
    *    caracteres de 0 a 9 e se utiliza algum caractere para separador de 
    *    agrupamento.
    --------------------------------------------------------------------------*/
    public static boolean numericTest(Locale locale)
    {
        String rExp =
            "1[" + separator(locale) + "]234[" + decimalPoint(locale) + "]56";
        return String.format(locale, "%,.2f", 1234.56).matches(rExp);
    }//fim de numericTest()
    
    /**
     * Retorna quantos digitos sao usados para os centavos em uma representacao
     * de moeda para o Locale passado como argumento. A maioria das moedas
     * possui centavos que podem ser expressos por dois digitos. E para os
     * Locales associados a estas moedas o metodo retornaria 2. Caso a moeda
     * associada ao Locale passado como argumento nao tenha centavos o metodo
     * retornarah zero.
     * <p>
     * Se tiver centavos, eh improvavel que alguma moeda usa mais de 2 digitos
     * para os centavos. Potem este metodo pode retornar qualquer valor entre
     * 0 e 3. Portanto caso uma moeda use mais de 3 digitos para centavos o 
     * metodo provavelmente retornaria 3. "Provavelmente" porque pode depender
     * da maneira como eh definido o arredondamento de valores para esta moeda.
     * 
     * @param locale O Locale.
     * 
     * @return Quantos digitos, para este Locale, a representacao monetaria
     * utiliza. 
     */
    /*[04]----------------------------------------------------------------------
    *    Retorna quantos digitos sao usados para centavos na representacao de
    *    moeda associada a um determinado Locale.
    --------------------------------------------------------------------------*/
    public static int centDigits(Locale locale)
    {
        String currency = 
            NumberFormat.getCurrencyInstance(locale).format(1.234);
        int length = currency.length(); int digits = -1; 
        for (int i = 0; i < length; i++)
            if (Character.isDigit(currency.charAt(i))) digits++;
        return digits;
    }//fim de centDigits()
    
    /**
     * Indica se a representacao monetaria para o Locale passado como argumento
     * utiliza centavos.
     * 
     * @param locale O Locale da representacao monetaria.
     * 
     * @return true se esta moeda utiliza centavos. false se nao.
     */
    /*[05]----------------------------------------------------------------------
    *    Retorna se uma determinada moeda associada ao Locale passado como 
    *    argumento ao metodo utiliza centavos.
    --------------------------------------------------------------------------*/
    public static boolean hasCents(Locale locale)
    {
        return (centDigits(locale) > 0); 
    }//fim de hasCents()
    
    /**
     * Exemplos de usos dos metodos da classe.
     * 
     * @param args Nao utilizado.
     */
    public static void main(String[] args)
    {
        System.out.println(separator(JA_JP));
        System.out.println(decimalPoint(FR_FR));
        
        Locale[] loc = (Locale.getAvailableLocales());
        for (Locale lc : loc) 
        {
            if (numericTest(lc))
                System.out.println(lc+" : "+String.format(lc,"%,.2f", 1234.56));
                   
        }
        System.out.println("-------------------------------------------");
        for (Locale lc : loc) 
        {
            if (!numericTest(lc))
                System.out.println(lc+" : "+String.format(lc,"%,.2f", 1234.56));
                   
        }
        System.out.println(centDigits(PT_BR));
        System.out.println(centDigits(JA_JP));
        System.out.println(centDigits(new Locale("dz")));    
  
    }//fim de main()
    
}//fim da classe LocaleTools
