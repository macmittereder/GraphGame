//
//  Pebbling.h
//  Playing the Game
//
//  Created by Matt Prudente on 11/20/13.
//  Copyright (c) 2013 Matt Prudente. All rights reserved.
//

#ifndef Playing_the_Game_Pebbling_h
#define Playing_the_Game_Pebbling_h

#include <iostream>
using namespace std;

#endif /* defined(__Path_Pebbling__PebblingGame__) */

class Pebbling{
public:
    
    bool MoverWin(int arr[]); //COMPLETE
    bool MoveExists(int l, int arr[]); //COMPLETE
    bool LegalMove(unsigned int m, unsigned int d); //COMPLETE
    bool ForwardMove(int i, int l, int arr[]);
    
    void Printarray(int l, int arr[]); //COMPLETE
    void MakePath(int l, int arr[]); //COMPLETE
    void Mover(int l, int arr[]); //COMPLETE
    //   void MinDamage(int l, int arr[]); //COMPLETE
    void Defender(int l, int arr[], int s, int e);//COMPLETE
    //   void MaxDamage(int l, int arr[]);//COMPLETE
    void Result(int l, int arr[]);//COMPLETE
    void MoveForward(int i, int arr[]);
    
private:
    unsigned int position=0;
    unsigned int defposition = 0;
    unsigned int first=0;
    
}game;

void Pebbling::MakePath (int l, int arr[]) // FINISHED
{
    int i;
    
    
    cout<< "Enter the initial configuration. (Enter a '-1' to place zeros at that place at and to the right)\n";
    
    for (i= 0; i < l; i ++)
    {
        cout << "v[" << i+1 << "] = ";
        cin >> arr[i];
        
        if (arr[i] <= -1)
        {
            break;
        }
        else
            continue;
    }
    
    for (int k = i; k < l ; k++)
    {
        arr[k] = 0;
    }
    
}

void Pebbling::Printarray(int l, int arr[]) // FINISHED
{
    
    for (int n = 0; n < l; n ++)
    {
        cout << arr[n] << " | ";
    }
    cout << endl;
}

bool Pebbling::MoverWin(int arr[]) // FINISHED
{
    if (arr[0]>=1) return true;
    return false;
}

bool Pebbling::MoveExists(int l, int arr[]) // FINISHED
{
    for (int k=0; k < l; k++)
    {
        if (arr[k] > 1) return true;
    }
    
    return false;
}

bool Pebbling::ForwardMove(int i, int l, int arr[])
{
    for(int j = i; j < l; j++)
    {
        if (arr[j] > 1)  return false;
        
    }
    return true;
}


void Pebbling::MoveForward(int i, int arr[])
{
    arr[i]-=2;
    arr[i-1]+=1;
}

void Pebbling::Mover(int l, int arr[]) // FINISHED
{
    for (int i = 0; i < l; i++)
    {
        
        if (arr[i] > 2 && (arr[i+1] == 2 || arr[i+1] == 3 ))   // Can the mover force the defender to move forward
        {
            
            if (ForwardMove(i+2, l, arr))
            {
                MoveForward(i+1, arr);
                position = i;
                break;
            }
            else
            {
                MoveForward(i, arr);
                position = i-1;
                break;
                
            }
        }
        
        else if (arr[i] == 2 && (arr[i+1] == 2 || arr[i+1] == 3 )) // a   2  2,3  anything other move situation
        {
            if (arr[i-1] == 1 && arr[i-2] == 1)  // Can the defender pull a 1 back
            {
                MoveForward(i, arr);
                position = i-1;
                break;
                
            }
            else
            {
                MoveForward(i+1, arr);
                position = i;
                break;
            }
        }
        else if (arr[i] > 1)   // normal move
        {
            MoveForward(i, arr);
            position = i-1;
            break;
        }
    }

    
    //cout<< position+ 1 << endl;
    Printarray(l, arr);
    
}

void Pebbling::Defender(int l, int arr[], int s, int e) // FINISHED
{
    s--;
    e--;

        if ( !MoverWin(arr) && MoveExists(l, arr))
            {
                arr[s]-=2;
                arr[e]+=1;
    
            }
    
    
    Printarray(l, arr);
}

bool Pebbling::LegalMove(unsigned int mov, unsigned int def) // FINISHED
{
    if (mov == def) return false;
    else return true;
}

void Pebbling::Result(int l, int arr[]) // FINISHED
{
    if (MoverWin(arr))
    {
        cout<< "The Mover Wins!\n\n";
    }
    
    if (!MoverWin(arr) && !MoveExists(l, arr)) {
        cout<< "The Defender Wins!\n\n";
    }
    
}

