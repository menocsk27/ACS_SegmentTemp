function diagramas_dispersion

    u=[2,4,6,8];
    v=[4,8,12,16];
   
    pearsonManual = pearson(u,v)
    pearsonMatlab = corrcoef(u,v)
    
end

%Función que calcula la esperanza, media o promedio de un conjunto de
%valores
function [esperanza] = calculateAvg(X1)
    
   if (isequal(size(X1),0))
       error('Arreglo no debe ser vacio.');
   end

   esperanza=0;
   N=(size(X1)) %size () devuelve filas y columnas
   n=N(2)*N(1) %Se obtiene el segundo valor referente al tamaño del arreglo, que pertenece a 
               %las columnas al ser un arreglo de una dimensión
   
   for idx = 1:1:N(1)
       for idy =1:1:N(2)
            esperanza(1)= esperanza(1)+ X1(idx,idy); %Suma de todos los elementos del arreglo
       end
   end
   
   esperanza(1) = esperanza(1) ./ n %Se normaliza 

end


%Función que calcula la varianza de un conjunto de valores
function [varianza] = calculateVar(X1)

    if (size(X1)==0)
       error('Arreglo no debe ser vacio.');
    end
   
   E=calculateAvg(X1);
   N=(size(X1));
   n=N(2)*N(1);%Se obtiene el segundo valor referente al tamaño del arreglo, que pertenece a 
            %las columnas al ser un arreglo de una dimensión
   varianza=0;
   for idx = 1:1:N(1)
       for idy =1:1:N(2)
        varianza(1)= varianza(1) + power((X1(idx,idy)-E(1)),2); %%Suma del cuadrado de la resta del elemento menos la esperanza
       end
   end
   varianza = varianza./n; %Se normaliza
end

%Función que calcula el coeficiente de pearson entre dos conjuntos de
%valores del mismo tamaño
function [coeficiente] = pearson(X1,X2) 

   if (size(X1)~=size(X2))
       error('Arreglos deben ser del mismo tamaño');
   end
   
   if(isequal(size(X1),0))
      error('Arreglos no deben ser vacios'); 
   end
       
   E1=calculateAvg(X1)
   E2=calculateAvg(X2);
   N=(size(X1));
   n=N(2)*N(1);%Se obtiene el segundo valor referente al tamaño del arreglo, que pertenece a 
            %las columnas al ser un arreglo de una dimensión
   
            
   %La covarianza es la suma de los productos resultados de la resta de los
   %elementos del primer conjunto menos la esperanza del primer conjunto y
   %la resta de los elementos del segundo conjunto menos la esperanza del
   %segundo conjunto
   covarianza=0;
   for idx = 1:1:N(1)
       for idy= 1:1:N(2)
        covarianza(1) = covarianza(1) + (X1(idx,idy)-E1(1))*(X2(idx,idy)-E2(1));
       end
   end
   
   covarianza=covarianza./n; %Se normaliza
   
   var1=calculateVar(X1)
   var2=calculateVar(X2)
   
   %Se obtiene las desviaciones estandar
   desvstd1 = sqrt(var1)
   desvstd2 = sqrt(var2)
   
   %Formula final 
   coeficiente=covarianza(1)./(desvstd1(1)*desvstd2(1));

end
