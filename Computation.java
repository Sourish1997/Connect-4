import java.util.*;
public class Computation
{
int diag1init[]={3,0,4,0,5,0,5,1,5,2,5,3};
int diag2init[]={2,0,1,0,0,0,0,1,0,2,0,3};
int diagSize[]={4,5,6,6,5,4};
int mainDepth;
boolean firstMinimaxCall=true;
ArrayList<Integer> childrenVals=new ArrayList<Integer>();
public int minimax(GameState game,int depth,int alpha,int beta)
{
if(firstMinimaxCall)
{
mainDepth=depth;
firstMinimaxCall=false;
}
if(depth!=mainDepth)
{
int val=isTerminal(game);
if(val!=0)
{
if(depth==(mainDepth-1))
{
childrenVals.add(alpha);
}
return val;
}
}
if(depth==0)
return returnVal(game);
if(game.maxPlayer)
{
ArrayList<GameState> children=game.generateChildren();
for(GameState child:children)
{
alpha=Math.max(alpha,minimax(child,depth-1,alpha,beta));
if(alpha>=beta)
break;
}
if(depth==(mainDepth-1))
{
childrenVals.add(alpha);
}
return alpha;
}
else
{
ArrayList<GameState> children=game.generateChildren();
for(GameState child:children)
{
beta=Math.min(beta,minimax(child,depth-1,alpha,beta));
if(alpha>=beta)
break;
}
if(depth==(mainDepth-1))
{
childrenVals.add(beta);
}
return beta;
}
}
public void refresh()
{
firstMinimaxCall=true;
childrenVals.clear();
}
public int isTerminal(GameState game)
{
int val=0;
val=winCheck(game);
if(val!=0)
return val;
val=canWin(game);
if(val!=0)
return val;
val=instantThreat(game);
if(val!=0)
return val;
if(boardFilled(game)==true)
val=5;
return val;
}
public int canWin(GameState game)
{
int disc;
int val;
int spaceX=0,spaceY=0;
if(game.maxPlayer)
{
disc=1;
val=10;
}
else
{
disc=2;
val=1;
}
for(int a=0;a<6;a++)
{
for(int b=0;b<4;b++)
{
int sum=0;
boolean spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[a][b+c]==disc)
sum++;
else if(game.board[a][b+c]==0)
{
spaceX=a;
spaceY=b+c;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
return val;
}
else
return val;
}
}
}
for(int a=0;a<7;a++)
{
for(int b=0;b<3;b++)
{
int sum=0;
boolean spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[b+c][a]==disc)
sum++;
else if(game.board[b+c][a]==0)
{
spaceX=b+c;
spaceY=a;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
return val;
}
else
return val;
}
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
for(int b=0,c=diag1init[init],d=diag1init[init+1];b<diagSize[a]-3;b++,c--,d++)
{
int sum=0;
boolean spaceCheck=false;
for(int e=0,f=c,g=d;e<4;e++,f--,g++)
{
if(game.board[f][g]==disc)
sum++;
else if(game.board[f][g]==0)
{
spaceX=f;
spaceY=g;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
return val;
}
else
return val;
}
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
for(int b=0,c=diag2init[init],d=diag2init[init+1];b<diagSize[a]-3;b++,c++,d++)
{
int sum=0;
boolean spaceCheck=false;
for(int e=0;e<4;e++)
{
if(game.board[c+e][d+e]==disc)
sum++;
else if(game.board[c+e][d+e]==0)
{
spaceX=c+e;
spaceY=d+e;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
return val;
}
else
return val;
}
}
}
return 0;
}
public int instantThreat(GameState game)
{
int disc;
int val;
int spaceX=0,spaceY=0;
if(game.maxPlayer)
{
disc=2;
val=1;
}
else
{
disc=1;
val=10;
}
int threats=0;
for(int a=0;a<6;a++)
{
for(int b=0;b<4;b++)
{
int sum=0;
boolean spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[a][b+c]==disc)
sum++;
else if(game.board[a][b+c]==0)
{
spaceX=a;
spaceY=b+c;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
threats++;
}
else
threats++;
}
}
}
for(int a=0;a<7;a++)
{
for(int b=0;b<3;b++)
{
int sum=0;
boolean spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[b+c][a]==disc)
sum++;
else if(game.board[b+c][a]==0)
{
spaceX=b+c;
spaceY=a;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
threats++;
}
else
threats++;
}
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
for(int b=0,c=diag1init[init],d=diag1init[init+1];b<diagSize[a]-3;b++,c--,d++)
{
int sum=0;
boolean spaceCheck=false;
for(int e=0,f=c,g=d;e<4;e++,f--,g++)
{
if(game.board[f][g]==disc)
sum++;
else if(game.board[f][g]==0)
{
spaceX=f;
spaceY=g;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
threats++;
}
else
threats++;
}
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
for(int b=0,c=diag2init[init],d=diag2init[init+1];b<diagSize[a]-3;b++,c++,d++)
{
int sum=0;
boolean spaceCheck=false;
for(int e=0;e<4;e++)
{
if(game.board[c+e][d+e]==disc)
sum++;
else if(game.board[c+e][d+e]==0)
{
spaceX=c+e;
spaceY=d+e;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
if(spaceX!=5)
{
if(game.isFilled(spaceX+1,spaceY))
threats++;
}
else
threats++;
}
}
}
if(threats>=2)
return val;
return 0;
}
public int winCheck(GameState game)
{
for(int a=0;a<6;a++)
{
for(int b=0;b<4;b++)
{
if(game.board[a][b]==1&&game.board[a][b+1]==1&&game.board[a][b+2]==1&&game.board[a][b+3]==1)
return 10;
if(game.board[a][b]==2&&game.board[a][b+1]==2&&game.board[a][b+2]==2&&game.board[a][b+3]==2)
return 1;
}
}
for(int a=0;a<7;a++)
{
for(int b=0;b<3;b++)
{
if(game.board[b][a]==1&&game.board[b+1][a]==1&&game.board[b+2][a]==1&&game.board[b+3][a]==1)
return 10;
if(game.board[b][a]==2&&game.board[b+1][a]==2&&game.board[b+2][a]==2&&game.board[b+3][a]==2)
return 1;
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
int diag[]=returnDiag1(game,init,diagSize[a]);
for(int b=0;b<diagSize[a]-3;b++)
{
if(diag[b]==1&&diag[b+1]==1&&diag[b+2]==1&&diag[b+3]==1)
return 10;
if(diag[b]==2&&diag[b+1]==2&&diag[b+2]==2&&diag[b+3]==2)
return 1;
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
int diag[]=returnDiag2(game,init,diagSize[a]);
for(int b=0;b<diagSize[a]-3;b++)
{
if(diag[b]==1&&diag[b+1]==1&&diag[b+2]==1&&diag[b+3]==1)
return 10;
if(diag[b]==2&&diag[b+1]==2&&diag[b+2]==2&&diag[b+3]==2)
return 1;
}
}
return 0;
}
public int returnVal(GameState game)
{
ArrayList<Integer> maxSpaces=new ArrayList<Integer>();
ArrayList<Integer> minSpaces=new ArrayList<Integer>();
int spaceX=0,spaceY=0;
for(int a=0;a<6;a++)
{
for(int b=0;b<4;b++)
{
int sum=0;
boolean spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[a][b+c]==1)
sum++;
else if(game.board[a][b+c]==0)
{
spaceX=a;
spaceY=b+c;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck==true)
{
maxSpaces.add(spaceX);
maxSpaces.add(spaceY);
}
sum=0;
spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[a][b+c]==2)
sum++;
else if(game.board[a][b+c]==0)
{
spaceX=a;
spaceY=b+c;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck==true)
{
minSpaces.add(spaceX);
minSpaces.add(spaceY);
}
}
}
for(int a=0;a<7;a++)
{
for(int b=0;b<3;b++)
{
int sum=0;
boolean spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[b+c][a]==1)
sum++;
else if(game.board[b+c][a]==0)
{
spaceX=b+c;
spaceY=a;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
maxSpaces.add(spaceX);
maxSpaces.add(spaceY);
}
sum=0;
spaceCheck=false;
for(int c=0;c<4;c++)
{
if(game.board[b+c][a]==2)
sum++;
else if(game.board[b+c][a]==0)
{
spaceX=b+c;
spaceY=a;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
minSpaces.add(spaceX);
minSpaces.add(spaceY);
}
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
for(int b=0,c=diag1init[init],d=diag1init[init+1];b<diagSize[a]-3;b++,c--,d++)
{
int sum=0;
boolean spaceCheck=false;
for(int e=0,f=c,g=d;e<4;e++,f--,g++)
{
if(game.board[f][g]==1)
sum++;
else if(game.board[f][g]==0)
{
spaceX=f;
spaceY=g;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
maxSpaces.add(spaceX);
maxSpaces.add(spaceY);
}
sum=0;
spaceCheck=false;
for(int e=0,f=c,g=d;e<4;e++,f--,g++)
{
if(game.board[f][g]==2)
sum++;
else if(game.board[f][g]==0)
{
spaceX=f;
spaceY=g;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
minSpaces.add(spaceX);
minSpaces.add(spaceY);
}
}
}
for(int a=0,init=0;a<6;a++,init+=2)
{
for(int b=0,c=diag2init[init],d=diag2init[init+1];b<diagSize[a]-3;b++,c++,d++)
{
int sum=0;
boolean spaceCheck=false;
for(int e=0;e<4;e++)
{
if(game.board[c+e][d+e]==1)
sum++;
else if(game.board[c+e][d+e]==0)
{
spaceX=c+e;
spaceY=d+e;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
maxSpaces.add(spaceX);
maxSpaces.add(spaceY);
}
sum=0;
spaceCheck=false;
for(int e=0;e<4;e++)
{
if(game.board[c+e][d+e]==2)
sum++;
else if(game.board[c+e][d+e]==0)
{
spaceX=c+e;
spaceY=d+e;
spaceCheck=true;
}
}
if(sum==3 && spaceCheck)
{
minSpaces.add(spaceX);
minSpaces.add(spaceY);
}
}
}
int winner=0;
for(int a=0;a<7;a++)
{
outer:
for(int b=4;b>=0;b--)
{
if(b%2==0)
{
for(int c=0;c<minSpaces.size();c+=2)
{
if(b==minSpaces.get(c)&&a==minSpaces.get(c+1))
{
winner=Math.max(winner,1);
break outer;
}
}
}
else
{
for(int c=0;c<maxSpaces.size();c+=2)
{
if(b==maxSpaces.get(c)&&a==maxSpaces.get(c+1))
{
winner=Math.max(winner,2);
break outer;
}
}
}
}
}
if(winner==2)
return 10;
else if(winner==1)
return 1;
else
return 5;
}
public int[] returnDiag1(GameState game,int init,int diagSize)
{
int diag[]=new int[diagSize];
for(int b=0,c=diag1init[init],d=diag1init[init+1];b<diagSize;b++,c--,d++)
{
diag[b]=game.board[c][d];
}
return diag;
}
public int[] returnDiag2(GameState game,int init,int diagSize)
{
int diag[]=new int[diagSize];
for(int b=0,c=diag2init[init],d=diag2init[init+1];b<diagSize;b++,c++,d++)
{
diag[b]=game.board[c][d];
}
return diag;
}
public boolean boardFilled(GameState game)
{
boolean decider=true;
for(int a=0;a<6;a++)
{
for(int b=0;b<7;b++)
{
if(game.board[a][b]==0)
decider=false;
}
}
return decider;
}
}