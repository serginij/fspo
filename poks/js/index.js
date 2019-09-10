const canvas = document.querySelector('#canvas');
const ctx = canvas.getContext('2d');

const isHunger = document.querySelector('#isHunger');
const isSick = document.querySelector('#isSick');
const isHungerButton = document.querySelector('#isHungerButton');

const activeImage = document.querySelector('#active');
const sleepImage = document.querySelector('#sleep');
const sickImage = document.querySelector('#sick');
const poopImage = document.querySelector('#poop');

ctx.fillStyle = 'green';

let image = activeImage;

class Person {
  constructor(name) {
    this.name = name;

    this.x = canvas.width / 2;
    this.y = canvas.height / 2;
    this.sleep = false;
    this.isHunger = false;
    this.lastFeed = new Date();
    this.poops = [];
    this.sick = false;
    this.imageSize = 40;
  }

  moveUp() {
    if (this.y - this.imageSize > 0 && !this.sleep) {
      this.y -= 1;
      this.render();
    }
  }

  moveDown() {
    if (this.y + this.imageSize < canvas.height && !this.sleep) {
      this.y += 1;
      this.render();
    }
  }

  moveRight() {
    if (this.x + this.imageSize < canvas.width && !this.sleep) {
      this.x += 1;
      this.render();
    }
  }

  moveLeft() {
    if (this.x > 0 && !this.sleep) {
      this.x -= 1;
      this.render();
    }
  }

  checkSleep() {
    let time = new Date().getHours();
    if (time < 8 || time >= 22) {
      this.sleep = true;
      this.render();
    } else {
      this.sleep = false;
    }
    console.log('check_sleep', this.sleep);
  }

  checkHunger() {
    // let time = new Date().getHours();
    let now = new Date();
    // change / 1000 to / 1000 /60/60
    let diff = (Date.parse(now) - Date.parse(this.lastFeed)) / 1000;
    //change 20 to 3
    if (diff >= 20 && !this.sleep) {
      this.isHunger = true;
      isHunger.style.backgroundColor = 'red';
    }
    console.log('check_hunger', this.isHunger);
  }

  feed() {
    this.isHunger = false;
    this.lastFeed = new Date();
    isHunger.style.backgroundColor = 'green';
    console.log('Feeded');

    this.poop();
  }

  poop() {
    let randomTime = Math.round(Math.random() * 10);
    setTimeout(() => {
      this.poops.push({ x: this.x, y: this.y });
      this.isSick();
    }, randomTime * 1000);
  }

  clearPoop() {
    this.poops.shift();
    this.render();
  }

  isSick() {
    if (this.poops.length >= 3) {
      this.sick = true;
      isSick.style.backgroundColor = 'red';
      this.render();
    } else {
      this.sick = false;
    }
  }

  cure() {
    this.sick = false;
    isSick.style.backgroundColor = 'green';
    this.render();
  }

  render() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    image = this.sick ? sickImage : this.sleep ? sleepImage : activeImage;

    ctx.drawImage(image, this.x, this.y, this.imageSize, this.imageSize);
    this.poops.map(poop => {
      ctx.drawImage(poopImage, poop.x, poop.y, 14, 14);
    });

    isHungerButton.disabled = this.sleep;
  }
}

const Tamagochi = new Person('tamagochi');

Tamagochi.render();

const feed = () => {
  Tamagochi.feed();
};

const clearPoop = () => {
  Tamagochi.clearPoop();
};

const cure = () => {
  Tamagochi.cure();
};

let randomNumber = 0;
setInterval(() => {
  randomNumber = Math.floor(Math.random() * 4);
}, 2000);

setInterval(() => {
  let functions = ['moveLeft', 'moveUp', 'moveRight', 'moveDown'];
  let func = functions[randomNumber];
  Tamagochi[func]();
}, 100);

setInterval(() => {
  Tamagochi.checkSleep();
  Tamagochi.checkHunger();
}, 6 * 1000);
