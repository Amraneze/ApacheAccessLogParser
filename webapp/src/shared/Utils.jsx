import { format, parseISO, isDate, } from 'date-fns';

const isEmpty = (obj) => obj === undefined ? true : (Array.isArray(obj) || typeof obj === 'string') ? obj.length === 0 : Object.entries(obj).length === 0 && obj.constructor === Object;

const unshift = (array, element) => array ? (array.unshift(element), array) : [element];

const reduceArray = (array, splitter) => array
    .reduce((acc, value, index) => acc.concat(value, index !== array.length - 1 ? splitter : ''), '');

const reduceArrayByField = (array, field) => array.reduce((acc, obj) => acc.concat(obj[field]), []);

const flattenObject = (obj) => Object.entries(obj).reduce((acc, [field, value]) =>
    typeof value === 'object' && !(Array.isArray(value)) && !(value instanceof Date) ?
            {...acc, ...flattenObject(value)} : {...acc, [field]: value} , {});

const getDate = (date) => date && format(typeof date === 'string' ? parseISO(date) : date, 'dd/MM/yyyy');

const isValidDate = (date) => date && (typeof date === 'object' 
    || (typeof date === 'string' && date.length >= 6))
     && isDate(typeof date === 'object' ? date : parseISO(date)); 

const getTime = (date) => format(typeof date === 'string' ? parseISO(date) : date, 'HH:mm');

/**
 * A function to capitalize the first letter of a word
 * @param {string} text to be capitalized
 * @returns the capitalized word or empty string if the argument is not a string
 */
const capitalize = (text) => typeof text === 'string' && `${text.charAt(0).toUpperCase()}${text.slice(1)}`;

/**
 * A function to capitalize the first letter of each word
 * @param {string} text that its words need to be capitalized
 * @returns the sentence with capitalized words or empty string if the argument is not a string
 */
const capitalizeSentence = (text) => typeof text === 'string' && text.split(' ').reduce((acc, _) => `${acc} ${capitalize(_)}`, '').trim();

const filterOnlyNumbers = (data) => data && /^[\d\s]*$/.test(data);

export const Utils = {
    isEmpty,
    unshift,
    capitalize,
    capitalizeSentence,
    flattenObject,
    reduceArray,
    reduceArrayByField,
    getDate,
    isValidDate,
    getTime,
    filterOnlyNumbers,
};
