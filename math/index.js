// Получаем доступ к html элементам
let words_count = document.querySelector('#words_count');
let in_out = document.querySelector('#in_out');
let v = document.querySelector('#v');
let v_ = document.querySelector('#v_');
let l = document.querySelector('#l');

// Инициализация переменных
let data = '';
let filtered = 0;
let input = 0;
// let In = 0,
// Out = 0;

const setData = value => {
  data = value;
};

const setFiltered = value => {
  filtered = value;
};

const setInput = value => {
  input = value;
};

const hanldeChange = e => {
  // console.log(e.length);
  setData(e);
};

const form = document.getElementById('form');

form.addEventListener('submit', e => {
  e.preventDefault();

  setFiltered(
    data
      // Убираем из текста все спец. символы (кол-во слов в тексте)
      .replace(/\.|\{|\}|\(|\)|\[|\]|!|\&|;|,|<|>|\?|\*|\:|\'/gi, ' ')
      .split(' ')
      // Убираем из текста пробелы
      .filter(el => el.trim().length > 2).length
  );

  setInput(
    // Поиск в тексте скобок с входными значениями в функции (входные значния)
    (data.replace(/\s/g, '').match(/\((.*?)\)/gi) || [])
      // Убираем из текста скобки
      .map(el => el.replace(/\)|\(/gi, ''))
      .filter(el => el.length)
      // Вычисление кол-ва входных значений
      .reduce((acc, el) => {
        if (el.split(',')) {
          el.split(',').map(item => acc++);
        } else {
          acc++;
        }
        return acc;
      }, 0) +
      // Поиск входных данных в стрелочных функциях (входные значения)
      (data.match(/\((.*?)\=>/gi) || [])
        // Убираем из текста спец. символы
        .map(el => el.replace(/\)|\(|>|\=/gi, '').trim())
        .filter(el => el.length).length +
      // Поиск ключевого слова для возвращения значений (выходные данные)
      (data.replace(/\s/g, '').match(/return(.*?);/gi) || []).length +
      // Проверка текста программы на наличие стрелочных функций в укороченной записи (выходные данные)
      (data.replace(/\s/g, '').match(/\=>(.*?)\)/gi) || []).filter(el => !el.match(/\=>{/)).length +
      // Фильтруем текст программы на соответсвие перебирающих методов массивов (выходные данные)
      (data.replace(/\s/g, '').match(/\.map|\.forEach|\.reduce/gi) || []).length
  );

  words_count.innerHTML = `Кол-во слов = ${filtered}`;
  in_out.innerHTML = `Ген. совок-ть вход/выход = ${input}`;
  v.innerHTML = `V = ${filtered * Math.log(filtered) ** 2}`;
  v_.innerHTML = `V* = ${(input + 2) * Math.log(input + 2)}`;
  l.innerHTML = `L = ${((input + 2) * Math.log(input + 2)) / (filtered * Math.log(filtered) ** 2)}`;

  // In =
  //   (data.match(/\((.*?)\=>/gi) || [])
  //     .map(el => el.replace(/\)|\(|>|\=/gi, '').trim())
  //     .filter(el => el.length).length +
  //   (data.replace(/\s/g, '').match(/\((.*?)\)/gi) || [])
  //     .map(el => el.replace(/\)|\(/gi, ''))
  //     .filter(el => el.length)
  //     .reduce((acc, el) => {
  //       if (el.split(',')) {
  //         el.split(',').map(item => acc++);
  //       } else {
  //         acc++;
  //       }
  //       return acc;
  //     }, 0);

  // Out = input - In;
  // console.log(In, Out, In * Out);
  // console.log(
  //   (data.replace(/\s/g, '').match(/\{(.*?)\}/gi) || []).filter(el => el.includes('return'))
  // );

  // input = In * Out;

  // in_out.innerHTML = `Геню совок-ть вход/выход = ${input}`;
  // v.innerHTML = `V = ${filtered * Math.log(filtered) ** 2}`;
  // v_.innerHTML = `V* = ${(input + 2) * Math.log(input + 2)}`;
  // l.innerHTML = `L = ${((input + 2) * Math.log(input + 2)) / (filtered * Math.log(filtered) ** 2)}`;
});

// const showValue = () => {
//   console.log(filtered, 'V = ', filtered * Math.log(filtered) ** 2);
// };

// const showInOut = () => {
//   console.log(filtered, input);
//   console.log(
//     (data.replace(/\s/g, '').match(/\((.*?)\)/gi) || [])
//       .map(el => el.replace(/\)|\(/gi, ''))
//       .filter(el => el.length),
//     (data.replace(/\s/g, '').match(/\((.*?)\)/gi) || [])
//       .map(el => el.replace(/\)|\(/gi, ''))
//       .filter(el => el.length)
//       .reduce((acc, el) => {
//         if (el.split(',')) {
//           el.split(',').map(item => acc++);
//         } else {
//           acc++;
//         }
//         return acc;
//       }, 0),
//     (data.match(/\((.*?)\=>/gi) || [])
//       .map(el => el.replace(/\)|\(|>|\=/gi, '').trim())
//       .filter(el => el.length),
//     (data.replace(/\s/g, '').match(/\{(.*?)\}/gi) || []).filter(el => el.includes('return')),
//     data.replace(/\s/g, '').match(/\=>[a-zA-Z]/gi) || [],
//     data.replace(/\s/g, '').match(/\.map|\.forEach|\.reduce/gi) || []
//   );
//   console.log(input);
//   console.log('V* = ', (input + 2) * Math.log(input + 2));
//   console.log('L = ', ((input + 2) * Math.log(input + 2)) / (filtered * Math.log(filtered) ** 2));
// };
