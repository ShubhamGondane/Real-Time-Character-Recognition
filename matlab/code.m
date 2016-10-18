clear all;
close all;
fid = fopen('C://Users//B D Gondane//Desktop//Major//Code//projectScaling//data.txt','r');
tline = fgetl(fid);
 i=1;
tableA = zeros(1000,1);
 
while ischar(tline)
    
 
     comma = char(44);
[A] = fscanf(fid, ['%f' comma]);
Z=complex(A(1,1),A(2,1));
 
tableA(i,1)=Z;
 
 
tline = fgetl(fid);
FD=fft(tableA);
plot(tableA,'.');
i=i+1;
end
    
 
disp(FD);
   
 
fclose(fid);
 

