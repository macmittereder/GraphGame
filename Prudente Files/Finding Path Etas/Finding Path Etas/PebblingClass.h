//
//  PebblingGame.h
//  Path Pebbling
//
//  Created by Matt Prudente on 11/4/13.
//  Copyright (c) 2013 Matt Prudente. All rights reserved.
//

#ifndef __Path_Pebbling__PebblingGame__
#define __Path_Pebbling__PebblingGame__

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
    void Defender(int l, int arr[]);//COMPLETE
    void MoveForward(int i, int arr[]);
    void MoveBackward(int i, int arr[]);
    
    
    void Result(int l, int arr[]);//COMPLETE
    int backwards=0;
    
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

void Pebbling::MoveBackward(int i, int arr[])
{
    arr[i]-=2;
    arr[i+1]+=1;
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
    
    
    
    //   Printarray(l, arr);
    
}


void Pebbling::Defender(int l, int arr[]) // FINISHED
{
    if ( !MoverWin(arr) && MoveExists(l, arr))
    {
        for (int i = 0; i < l; i++)
        {
            
            if (arr[i] > 1)
            {
                
                defposition = i;
                
                if (LegalMove(position, defposition)) break;
                else continue;
            }
        }
        
        if (defposition == position)                                // Is the only legal move to move forward?
        {
            MoveForward(defposition, arr);
        }
        
        else if (defposition == l-1)                                // Is the only legal move at the end of the path?
        {
            MoveForward(defposition, arr);
        }
        
        else if (arr[defposition] == 2 && arr[defposition-1] == 1 && arr[position] == 2)  // Is there a 1 1 1 2 0 1 2 m situation?
        {
            MoveForward(defposition, arr);
        }
        
        else if (defposition+1 == l-1)                             //Is the first move the 2nd last vertex?
        {
            MoveBackward(defposition, arr);
            backwards++;
        }
        
        
        else if (arr[defposition+1]==2 && arr[position] > 1)                             //Does the mover have a move besides forcing defender forward?
        {
            MoveBackward(defposition, arr);
            
        }
        
        else if (arr[defposition+1]==2)                             //Can the mover force the defender forward?
        {
            MoveBackward(defposition+1, arr);
            
        }
        
        else                                                        //Normal pebbling move
        {
            MoveBackward(defposition, arr);
            
        }
    }
    
    //  Printarray(l, arr);
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