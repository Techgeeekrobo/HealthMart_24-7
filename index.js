// let x=document.querySelector('button');
// x.addEventListener('click',output);
// x.addEventListener('click',input);
// function output
// {
//     alert('Hello World!!!');
// }
// function input()
// {
//     let name=prompt('Enter Name of Student');
//     x.textContent='hello '+name;
// }



// var x=5; //number
// var x="5"; //string
// var x=true; //boolean
// var x=null; //object
// var x;
// var x=BigInt(92837529475);
// var x=52947384738n;
// console.log(typeof x);



// let x='yash';
// x='vatsal';
// let x=35;
// console.log(x);



// var x=10, y=2, z;
// z=x+y;
// console.log(z);
// z=x-y;
// z=x*y;
// z=x/y;
// z=x;
// z=x%y;
// z=x**y;
// z=x==y;
// y='10'; z=x==y;
// z=x===y;
// z=x!=y;
// y='10'; z=x!=y;
// z=x!==y;
// z=x>y;
// y=20; z=x>=y;
// z=x<y;
// y=10; z=x<=y;
// z=x++; x;
// z=++x; x;
// z=x--; x;
// z=--x; x;
// x=-x; x;
// x=-x; x;
// x='10'; z=x;
// x=+x; x;
// x=-x; x;
// x=++x; x;
// x=--x; x;
// x=true; x=+x; x;
// x=false; x=+x; x;
// x=true; x=-x; x;
// x=false; x=-x; x;
// x="Hello", y="World";
// z=x+" "+y+"!";



// let time=prompt('Hey what\'s time');
// if(time>=4 && time<=12)
//     console.log('Hello, Good Morning!');

// else if(time>=13 && time<=16)
//     console.log('Hello, Good Afternoon!');

// else
//     console.log('Hello, Good Night!');



// let chap=prompt("kai chalu karu?");
// switch(chap)
//     {
//         case 'fan':
//             console.log('fan chalu kari didho chhe.');
//             break;
        
//         case 'light':
//             console.log('light chalu kari didho chhe.');
//             break;

//         case 'AC':
//             console.log('AC chalu kari didho chhe.');
//             break;

//         case 'fridge':
//             console.log('fridge chalu kari didho chhe.');
//             break;

//         case 'gas':
//             console.log('gas chalu kari didho chhe.');
//             break;
        
//         default:
//             console.log('chap nathi malti.');
//     }
// console.log('Biju kai chalu karvanu chhe?');



// for(let i=0;i<10;i++)
//     {
//         console.log(2**i);
//     }

// let i=0;
// while(i<10)
//     {
//         console.log(i**i);
//         i++;
//     }

// let i=0;
// do
// {
//     console.log(10**i);
//     i++;
// }while(i<10)



// function fun(A,B)
// {
//     console.log(a+b);
// }
// let a=4, b=10;
// fun(a,b);

// function fun(A,B)
// {
//     let C=a+B;
//     return C;
// }
// let a=20, b=30, c;
// c=fun(a,b);
// console.log(c);



// let a='123';
// let b=Number(a);
// console.log(b);
// let c=b.toString();
// console.log(c);
// var intro='My name is vatsal.';
// console.log(intro.length);
// console.log(intro[3]);
// console.log(intro.indexOf('name'));
// console.log(intro.slice(3,5));
// console.log(intro.toUpperCase());
// console.log(intro.toLowerCase());



let vatsal=
{
    surname: "Ranpariya",
    height: 6,
    nationality: "Indian",
    result: 10
}
// console.log(vatsal);
// console.log(vatsal.height);
// console.log(vatsal["nationality"]);
// let marks="result";
// console.log(vatsal[marks]);



// let subject=["English","Gujarati","science","Sanskrit","Hindi","Maths","Chemistry","Physics","Biology","Computer","P.T."];
// console.log(subject[5]);
// console.log(subject.length);


// function createObject()
// {
//     return{
//         itemName: "gun",
//         price: 300,
//         discount: 10,
//         note:
//         {
//             make()
//             {console.log("product was created.")}
            
//         }
//     }
// }

// let Product=createObject();
// console.log(Product);
// console.log(Product.note.make());

factory function
function createObject(item)
{
    return{
        itemName: item,
        price: 300,
        discount: 10,
        note:
        {
            make()
            {console.log("product was created.")}
            
        }
    }
}

let Product=createObject("car");
// console.log(Product);
// console.log(Product.note.make());

//constructor function
function Create(item)
{
    this.itemName=item,
    this.price=300,
    this.discount=10,
    this.make=function(){console.log("product was created.")}
}

let product=new Create("toy");
// console.log(product);

// delete product.price;
// console.log(product);

// product.bhav=500;
// product.make=function(){console.log("vastu bani gai");}
// console.log(product);

console.log(product.constructor);
console.log(Product.constructor);