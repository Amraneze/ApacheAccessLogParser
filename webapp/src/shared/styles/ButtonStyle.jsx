const btnColor = '#009ee0';
const textColor = 'white';

const ButtonStyle = {
    minWidth: '15rem',
    padding: '.6rem 1.5rem',
    borderRadius: 60,
    boxShadow: '0 3px 6px 0 rgba(0, 0, 0, 0.24)',
    border: `solid 2px ${btnColor}`,
    backgroundColor: `${btnColor}`,
    fontSize: '1.6rem',
    fontWeight: 'bold',
    textAlign: 'center',
    textTransform: 'none',
};

export const PrimaryButtonStyle = {
    ...ButtonStyle,
    color: textColor,
    '&:hover': {
        color: btnColor,
    }
};

export const SecondaryButtonStyle = {
    ...ButtonStyle,
    color: textColor,
    backgroundColor: textColor,
};