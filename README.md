# Plagiarism-Detector
A Java command-line application that computes the similarity of two or more doccuments

## About
Name: Daire Ní Chatháin
ID: G00334757

#### Contents:
          1. How to run the application
          2. Project Design
          
## How to Run the Application
### Clone this repo
```bash
git clone https://github.com/DaireNiC/Plagiarism-Detector
```
### Navigate to the folder
```bash
cd PlagiarismDetector
```
### Run JAR file:
```bash
 java –cp ./oop.jar ie.gmit.sw.Runner
```
The user will then be presented with a menu. From here the user can enter the paths
to the files they wish to compare. The Jaccard similarity is then calculated and presented
to the user. 

## Project Design

![alt text](https://github.com/DaireNiC/Plagiarism-Detector/blob/master/design.png)

Throughout the design and development of the API, great effort was dedicated
to upholding the principles of loose-coupling and high cohesion. This was achieved by applying
abstraction, encapsulation, composition and inheritance.

The SRP principle is evident in the number of classes in the application. Each class has a single role, and could be reused in different cases. The interfaces included such as e.g ShingleCreator and MinHashator, provide a clear outline of the desired behaviour of the API's classes. 
Inheritance is demonstrated with the inclusion of the abstract class Parser which outlines the parse() method. This is inherited by the FileParser class which is in turn used by the FileToShingleParser class. I chose inheritance in this case over an interface as I believe FileToShingleParser is a specialised type of FileParser. 

