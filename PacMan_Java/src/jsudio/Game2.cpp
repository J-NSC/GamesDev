#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char *argv[])
{
    int fd, fdw;
    int pid;

    pid = fork();
    if (pid == 0)
    {
        execl("/bin/ls", "ls", "-l", NULL);
        _exit(2);
    }

    if (waitpid(pid) < 0)
    {
        printf("Wait error\n");
    }

    pid = fork();
    if (pid == 0)
    {
        fd = open("Hello.txt", O_RDONLY);
        fdw = open("World.txt", O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);

        char buffer[50];
        ssize_t nrd;
        while (nrd = read(fd, buffer, 50))
        {
            printf("file content: %s", buffer);
            write(fdw, buffer, nrd);
        }

        close(fd);
        close(fdw);
        _exit(fd);
    }

    if (waitpid(pid) < 0)
    {
        printf("Wait error occurs when opening file\n");
    }

    _exit(0);
}